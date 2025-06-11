package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

private const val KEY_QUESTION_INDEX = "question_index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    private lateinit var cheatButton: Button

    private lateinit var prevButton: Button
    private lateinit var nextButton: Button

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    private val answersViewModel: UserAnswersViewModel by lazy {
        ViewModelProvider(this)[UserAnswersViewModel::class.java]
    }

    /** `true` - пользователь дал ответ на все вопросы, иначе `false` */
    private val isAllQuestionsAnswered: Boolean
        get() = answersViewModel.answersCount == quizViewModel.questionsCount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tryPutSavedData(savedInstanceState)

        configureButtons()
        configureQuestionText()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(KEY_QUESTION_INDEX, quizViewModel.questionIndex)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT)
        {
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
                ?: false
        }
    }

    private fun tryPutSavedData(savedInstanceState: Bundle?) {
        val currentIndex = savedInstanceState
            ?.getInt(KEY_QUESTION_INDEX, 0)
            ?: 0
        quizViewModel.questionIndex = currentIndex
    }

    private fun configureButtons() {
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        cheatButton = findViewById(R.id.cheat_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)

        trueButton.setOnClickListener {
            setAnswer(true)
        }
        falseButton.setOnClickListener {
            setAnswer(false)
        }
        cheatButton.setOnClickListener {
            startCheatActivity()
        }
        prevButton.setOnClickListener {
            prevQuestion()
        }
        nextButton.setOnClickListener {
            nextQuestion()
        }

        updateNavButtons()
    }

    private fun configureQuestionText() {
        questionTextView = findViewById(R.id.question_text_view)
        updateQuestion()
    }

    /** Перейти к предыдущему вопросу */
    private fun prevQuestion() {
        quizViewModel.moveToPrevQuestion()
        updateQuestion()
    }

    /** Перейти к следующему вопросу */
    private fun nextQuestion() {
        quizViewModel.moveToNextQuestion()
        updateQuestion()
    }

    private fun updateQuestion() {
        questionTextView.setText(quizViewModel.questionText)

        updateAnswersButtons()
        updateCheatButton()
    }

    private fun updateAnswersButtons() {
        val hasAnswer = answersViewModel.getAnswer(quizViewModel.questionIndex) != null
        val visibility = if (hasAnswer) INVISIBLE else VISIBLE

        trueButton.visibility = visibility
        falseButton.visibility = visibility
    }

    private fun updateCheatButton() {
        if (answersViewModel.getAnswer(quizViewModel.questionIndex) == null) {
            cheatButton.visibility = VISIBLE
            return
        }

        cheatButton.visibility = INVISIBLE
    }

    private fun updateNavButtons() {
        if (isAllQuestionsAnswered) {
            prevButton.visibility = INVISIBLE
            nextButton.visibility = INVISIBLE
        }
    }

    /**
     * Дать ответ на вопрос
     * @param userAnswer ответ на вопрос
     */
    private fun setAnswer(userAnswer: Boolean) {
        answersViewModel.setAnswer(quizViewModel.questionIndex, userAnswer)

        updateAnswersButtons()
        updateCheatButton()
        updateNavButtons()

        showAnswerResult(userAnswer)
        tryShowTotalResult()

        resetIsCheater()
    }

    private fun showAnswerResult(userAnswer: Boolean) {
        val messageResId = when {
            userAnswer == quizViewModel.questionAnswer && quizViewModel.isCheater ->
                R.string.judgment_toast
            userAnswer == quizViewModel.questionAnswer ->
                R.string.correct_toast
            else ->
                R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    /**
     * Отобразить сообщение с количеством верных ответов,
     * если пользователь ответил на все вопросы
     */
    private fun tryShowTotalResult() {
        if (!isAllQuestionsAnswered) {
            return
        }

        val correctAnswers = quizViewModel.getCorrectAnswers()
        val userCorrectAnswersCount = answersViewModel.getUserCorrectAnswersCount(correctAnswers)

        val message = getString(
            R.string.correct_answers,
            userCorrectAnswersCount,
            correctAnswers.size)
        Toast
            .makeText(this, message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun resetIsCheater() {
        quizViewModel.isCheater = false
    }

    private fun startCheatActivity() {
        val intent = CheatActivity.newIntent(
            this@MainActivity,
            quizViewModel.questionAnswer)
        startActivityForResult(intent, REQUEST_CODE_CHEAT)
    }
}