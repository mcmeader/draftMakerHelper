package com.example.draftmakerhelper

import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.util.logging.Logger

class ButtonHandler {
    var randomNumbers: MutableList<Int> = mutableListOf()
    var currentIndex: Int = 0

    fun buttonClicked(
        submitted: Boolean,
        displayText: TextView,
        textLabel: TextView,
        editText: EditText
    ) {
        val randomHandler = ListGenerator()
        if (submitted) {
            textLabel.visibility = View.INVISIBLE
            displayText.visibility = View.VISIBLE
            editText.visibility = View.INVISIBLE
            randomHandler.setNumberofEntries(editText.text.toString().toInt())

            randomNumbers.addAll(randomHandler.getRandomList())
            displayText.text = randomNumbers[currentIndex].toString()
            currentIndex++

        } else {
            if (currentIndex < randomNumbers.size) {
                displayText.text = randomNumbers[currentIndex].toString()
                currentIndex++
            } else {
                displayText.text = "Done"
            }
        }


    }

    fun resetButtonClicked(
        displayText: TextView,
        textLabel: TextView,
        editText: EditText
    ) {
        textLabel.visibility = View.VISIBLE
        displayText.visibility = View.INVISIBLE
        displayText.text = ""
        editText.visibility = View.VISIBLE
        editText.text.clear()
        currentIndex = 0
        randomNumbers.clear()
    }
}