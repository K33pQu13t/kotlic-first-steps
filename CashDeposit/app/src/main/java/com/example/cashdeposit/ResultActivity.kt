package com.example.cashdeposit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val EXTRA_CASH_AMOUNT = "com.enfranchiser.android.cash-deposit.cash-amount"
private const val EXTRA_PERCENT = "com.enfranchiser.android.cash-deposit.percent"

class ResultActivity : AppCompatActivity() {
    companion object {
        fun newIntent(packageContext: Context, cashAmount: Int, percent: Int): Intent {
            return Intent(packageContext, ResultActivity::class.java).apply {
                putExtra(EXTRA_CASH_AMOUNT, cashAmount)
                putExtra(EXTRA_PERCENT, percent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        configureTextViews()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configureTextViews() {
        val cashAmount = intent.getIntExtra(EXTRA_CASH_AMOUNT, 0)
        val percent = intent.getIntExtra(EXTRA_PERCENT, 0)
        val cashResult = cashAmount + (cashAmount * percent) / 100

        val resultTextView = findViewById<TextView>(R.id.result_text_view)
        resultTextView.text = getString(R.string.cash_result, cashResult)
    }
}