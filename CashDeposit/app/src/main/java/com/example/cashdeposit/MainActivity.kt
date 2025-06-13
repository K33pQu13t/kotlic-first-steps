package com.example.cashdeposit

import android.os.Bundle
import android.widget.EditText
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider

private const val KEY_CASH_AMOUNT = "cash_amount"
private const val KEY_PERCENT = "percent"

private const val percentBy3Months = 3
private const val percentBy6Months = 5
private const val percentBy12Months = 9

class MainActivity : AppCompatActivity() {
    private val depositSettingViewModel: DepositSettingViewModel by lazy {
        ViewModelProvider(this)[DepositSettingViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tryPutSavedData(savedInstanceState)

        configureRadioGroups()
        configureEditTexts()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (depositSettingViewModel.cashAmount != null) {
            outState.putInt(KEY_CASH_AMOUNT, depositSettingViewModel.cashAmount!!)
        }

        if (depositSettingViewModel.percent != null) {
            outState.putInt(KEY_PERCENT, depositSettingViewModel.percent!!)
        }
    }

    private fun tryPutSavedData(savedInstanceState: Bundle?) {
        depositSettingViewModel.cashAmount = savedInstanceState
            ?.getInt(KEY_CASH_AMOUNT)

        depositSettingViewModel.percent = savedInstanceState
            ?.getInt(KEY_PERCENT)
    }

    private fun configureRadioGroups() {
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            onRadioGroupCheckedChange(checkedId)
        }

        val checkedOptionId = when (depositSettingViewModel.percent) {
            percentBy3Months
                -> R.id.radio_deposit_3_months
            percentBy6Months
                -> R.id.radio_deposit_6_months
            percentBy12Months
                -> R.id.radio_deposit_12_months
            else
                -> R.id.radio_deposit_3_months
        }
        radioGroup.check(checkedOptionId)
    }

    private fun configureEditTexts() {
        val depositAmountInput = findViewById<EditText>(R.id.deposit_amount_input)
        depositAmountInput.doAfterTextChanged { text ->
            onCashAmountChanged(text.toString().toIntOrNull())
        }

        depositAmountInput.setText(depositSettingViewModel.cashAmount?.toString())
    }

    private fun onRadioGroupCheckedChange(checkedId: Int) {
        when (checkedId) {
            R.id.radio_deposit_3_months -> {
                depositSettingViewModel.percent = percentBy3Months
            }
            R.id.radio_deposit_6_months -> {
                depositSettingViewModel.percent = percentBy6Months
            }
            R.id.radio_deposit_12_months -> {
                depositSettingViewModel.percent = percentBy12Months
            }
        }
    }

    private fun onCashAmountChanged(cashAmount: Int?) {
        depositSettingViewModel.cashAmount = cashAmount
    }
}