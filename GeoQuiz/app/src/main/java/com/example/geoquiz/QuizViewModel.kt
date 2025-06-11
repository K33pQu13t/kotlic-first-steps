package com.example.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )

    var questionIndex: Int = 0

    val questionsCount: Int
        get() = questionBank.size

    /** Ответ на текущий вопрос */
    val questionAnswer: Boolean
        get() = questionBank[questionIndex].answer

    val questionText: Int
        get() = questionBank[questionIndex].textResId

    var isCheater: Boolean = false

    fun moveToNextQuestion() {
        questionIndex = (questionIndex + 1) % questionBank.size
    }

    fun moveToPrevQuestion() {
        questionIndex = (questionIndex - 1 + questionBank.size) % questionBank.size
    }

    fun getCorrectAnswers(): List<Boolean> {
        return questionBank.map { it.answer }
    }
}