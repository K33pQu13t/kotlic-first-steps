package com.example.cashdeposit

import android.os.Bundle
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        configureRadioGroups()

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
    }

    private fun onRadioGroupCheckedChange(checkedId: Int) {
        when (checkedId) {
            R.id.radio_deposit_3_months -> {
                // Выбрано 3 месяца
            }
            R.id.radio_deposit_6_months -> {
                // Выбрано 6 месяцев
            }
            R.id.radio_deposit_12_months -> {
                // Выбран год
            }
        }
    }
}