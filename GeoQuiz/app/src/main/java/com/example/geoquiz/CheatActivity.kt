package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

private const val EXTRA_ANSWER = "com.bignerdranch.android.geoquiz.answer"
const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"

private const val KEY_WAS_CHEATED = "was_cheated"

class CheatActivity : AppCompatActivity() {
    companion object {
        fun newIntent(packageContext: Context, answer: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER, answer)
            }
        }
    }

    private var answer = false

    private lateinit var answerTextView: TextView
    private lateinit var bundleVersionTextView: TextView
    private lateinit var showAnswerButton: Button

    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProvider(this)[CheatViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cheat)

        tryPutSavedData(savedInstanceState)

        answer = intent.getBooleanExtra(EXTRA_ANSWER, false)

        configureTextViews()
        updateTextViews()

        configureButtons()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(KEY_WAS_CHEATED, cheatViewModel.wasCheated)
    }

    private fun tryPutSavedData(savedInstanceState: Bundle?) {
        cheatViewModel.wasCheated = savedInstanceState
            ?.getBoolean(KEY_WAS_CHEATED, false)
            ?: false

        setIsAnswerShown(cheatViewModel.wasCheated)
    }

    private fun configureButtons() {
        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener {
            setIsAnswerShown(true)
            updateTextViews()
        }
    }

    private fun configureTextViews() {
        answerTextView = findViewById(R.id.answer_text_view)
        bundleVersionTextView = findViewById(R.id.bundle_version_text_view)

        val apiLevel = Build.VERSION.SDK_INT
        bundleVersionTextView.text = getString(R.string.api_level, apiLevel)
    }

    private fun updateTextViews() {
        if (!cheatViewModel.wasCheated) {
            return
        }

        val answerText = getAnswerText()
        answerTextView.setText(answerText)
    }

    private fun getAnswerText(): Int {
        val answerText = when {
            answer -> R.string.true_button
            else -> R.string.false_button
        }

        return answerText
    }

    private fun setIsAnswerShown(isAnswerShown: Boolean) {
        cheatViewModel.wasCheated = isAnswerShown

        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, cheatViewModel.wasCheated)
        }

        setResult(Activity.RESULT_OK, data)
    }
}