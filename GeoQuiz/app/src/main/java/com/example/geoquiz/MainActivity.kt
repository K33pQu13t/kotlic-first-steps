package com.example.geoquiz

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

class MainActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    private lateinit var prevButton: Button
    private lateinit var nextButton: Button

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    private val answersViewModel: UserAnswersViewModel by lazy {
        ViewModelProvider(this)[UserAnswersViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        configureButtons()
        configureQuestionText()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configureButtons() {
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)

        trueButton.setOnClickListener {
            setAnswer(true)
        }
        falseButton.setOnClickListener {
            setAnswer(false)
        }
        prevButton.setOnClickListener {
            prevQuestion()
        }
        nextButton.setOnClickListener {
            nextQuestion()
        }
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
    }

    private fun updateAnswersButtons() {
        val hasAnswer = answersViewModel.getAnswer(quizViewModel.questionIndex) != null
        val visibility = if (hasAnswer) INVISIBLE else VISIBLE

        trueButton.visibility = visibility
        falseButton.visibility = visibility
    }

    /**
     * Проверить ответ на вопрос
     * @param userAnswer ответ на вопрос
     */
    private fun setAnswer(userAnswer: Boolean) {
        answersViewModel.setAnswer(quizViewModel.questionIndex, userAnswer)
        updateAnswersButtons()

        val messageResId = if (userAnswer == quizViewModel.questionAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}