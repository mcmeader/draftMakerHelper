package com.example.draftmakerhelper

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var staticText: TextView
    lateinit var cardImage: ImageView
    lateinit var toggleCore: ToggleButton
    lateinit var pickWeakness: Button
    lateinit var progressBar: ProgressBar
    var coreChecked: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        staticText = findViewById(R.id.staticText)
        cardImage = findViewById(R.id.cardImage)
        toggleCore = findViewById(R.id.toggleCore)
        pickWeakness = findViewById(R.id.pickWeakness)
        progressBar = findViewById(R.id.progressBar)

        toggleCore.setOnClickListener {
            coreChecked = !coreChecked
        }

        pickWeakness.setOnClickListener {
            ApiCalls().getWeaknesses(this, coreChecked, cardImage, staticText,progressBar)
        }
    }
}
