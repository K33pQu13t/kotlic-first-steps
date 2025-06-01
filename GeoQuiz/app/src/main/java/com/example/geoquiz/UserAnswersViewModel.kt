package com.example.geoquiz

import androidx.lifecycle.ViewModel

class UserAnswersViewModel : ViewModel() {
    private val answers = mutableListOf<Boolean?>()

    val answersCount: Int
        get() = answers.count { it != null }

    fun setAnswer(index: Int, answer: Boolean) {
        if (index < 0) {
            return
        }

        while (index >= answers.size) {
            answers.add(null)
        }

        answers[index] = answer
    }

    fun getAnswer(index: Int): Boolean? {
        return if (index in answers.indices) {
            answers[index]
        } else {
            null
        }
    }

    /**
     * Получить число верных ответов пользователя
     * @param rightAnswers верные ответы
     * @return число верных ответов пользователя
     */
    fun getUserCorrectAnswersCount(rightAnswers: List<Boolean>): Int {
        val questionsCountToCheck = minOf(answers.size, rightAnswers.size)
        var rightAnswersCount = 0

        for (index in 0 until questionsCountToCheck) {
            if (answers[index] == rightAnswers[index]) {
                rightAnswersCount++
            }
        }

        return rightAnswersCount
    }
}