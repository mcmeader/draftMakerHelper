package com.example.draftmakerhelper

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var displayText: TextView
    lateinit var textLabel: TextView
    lateinit var editText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        displayText = findViewById(R.id.displayText)
        editText = findViewById(R.id.editText)
        textLabel = findViewById(R.id.textLabel)
        val submitAndNextButton: Button = findViewById(R.id.submitAndNext)
        val resetButton: Button = findViewById(R.id.resetButton)


        val buttonHandler = ButtonHandler()

        submitAndNextButton.setOnClickListener {
            buttonHandler.buttonClicked(
                textLabel.visibility == View.VISIBLE,
                displayText,
                textLabel,
                editText
            )
        }

        resetButton.setOnClickListener {
            buttonHandler.resetButtonClicked(
                displayText,
                textLabel,
                editText
            )
        }

    }
}
