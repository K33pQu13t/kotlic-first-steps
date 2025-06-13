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

class MainActivity : AppCompatActivity() {
    private val depositSettingViewModel: DepositSettingViewModel by lazy {
        ViewModelProvider(this)[DepositSettingViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        configureRadioGroups()
        configureEditTexts()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configureRadioGroups() {
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            onRadioGroupCheckedChange(checkedId)
        }

        onRadioGroupCheckedChange(radioGroup.checkedRadioButtonId)
    }

    private fun configureEditTexts() {
        val depositAmountInput = findViewById<EditText>(R.id.deposit_amount_input)
        depositAmountInput.doAfterTextChanged { text ->
            onCashAmountChanged(text.toString().toIntOrNull())
        }
    }

    private fun onRadioGroupCheckedChange(checkedId: Int) {
        when (checkedId) {
            R.id.radio_deposit_3_months -> {
                depositSettingViewModel.percent = 3
            }
            R.id.radio_deposit_6_months -> {
                depositSettingViewModel.percent = 5
            }
            R.id.radio_deposit_12_months -> {
                depositSettingViewModel.percent = 9
            }
        }
    }

    private fun onCashAmountChanged(cashAmount: Int?) {
        depositSettingViewModel.cashAmount = cashAmount
    }
}