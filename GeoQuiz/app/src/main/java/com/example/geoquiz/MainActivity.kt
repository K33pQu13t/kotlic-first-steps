package com.example.geoquiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var questionTextView: TextView

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button

    private var questionBank: List<Question>? = null
    private var questionIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initQuestionBank()

        configureQuestionText()
        configureButtons()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configureButtons() {
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)

        trueButton.setOnClickListener { view: View ->
            Toast
                .makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT)
                .show()
        }
        falseButton.setOnClickListener { view: View ->
            Toast
                .makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT)
                .show()
        }
        nextButton.setOnClickListener {
            nextQuestion()
        }
    }

    private fun configureQuestionText() {
        questionTextView = findViewById(R.id.question_text_view)

        val questionTextResId = questionBank!![questionIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun initQuestionBank() {
        questionBank = listOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_africa, false),
            Question(R.string.question_americas, true),
            Question(R.string.question_asia, true),
        )
    }

    /** Перейти к следующему вопросу */
    private fun nextQuestion() {
        questionIndex = (questionIndex + 1) % questionBank!!.size
        val questionTextResId = questionBank!![questionIndex].textResId
        questionTextView.setText(questionTextResId)
    }
}