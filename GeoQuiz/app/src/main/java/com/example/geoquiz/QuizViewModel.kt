package com.example.geoquiz

import androidx.lifecycle.ViewModel

private const val cheatingMaxCount = 3

class QuizViewModel : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )

    var cheatedQuestionIndexes = mutableListOf<Int>()

    var questionIndex: Int = 0

    val questionsCount: Int
        get() = questionBank.size

    /** Ответ на текущий вопрос */
    val questionAnswer: Boolean
        get() = questionBank[questionIndex].answer

    val questionText: Int
        get() = questionBank[questionIndex].textResId

    val canCheat: Boolean
        get() = cheatedQuestionIndexes.size < cheatingMaxCount

    val isCheater: Boolean
        get() = cheatedQuestionIndexes.contains(questionIndex)

    fun moveToNextQuestion() {
        questionIndex = (questionIndex + 1) % questionBank.size
    }

    fun moveToPrevQuestion() {
        questionIndex = (questionIndex - 1 + questionBank.size) % questionBank.size
    }

    fun getCorrectAnswers(): List<Boolean> {
        return questionBank.map { it.answer }
    }

    fun setWasCheated() {
        if (!cheatedQuestionIndexes.contains(questionIndex)) {
            cheatedQuestionIndexes.add(questionIndex)
        }
    }
}