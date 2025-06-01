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
}