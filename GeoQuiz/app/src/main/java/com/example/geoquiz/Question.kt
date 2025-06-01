package com.example.geoquiz

import androidx.annotation.StringRes

/**
 * Модель вопроса викторины
 * @property textResId идентификатор вопроса
 * @property answer ответ на вопрос (`true`/`false`)
 */
data class Question(
    @StringRes val textResId: Int,
    val answer: Boolean)