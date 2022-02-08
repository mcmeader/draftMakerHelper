package com.example.draftmakerhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.room.Room
import com.example.draftmakerhelper.constants.SubTypeCodes
import com.example.draftmakerhelper.models.CardFetchDataFields
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainScreen : AppCompatActivity() {
    var currentCardType = SubTypeCodes.weakness

    lateinit var calls: ApiCalls
    lateinit var titleText: TextView
    lateinit var cardImage: ImageView
    lateinit var fetchCards: Button
    lateinit var cardTypeButton: ToggleButton
    lateinit var actionButton: Button
    lateinit var spinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main_screen)

        cardImage = findViewById(R.id.cardImage)
        titleText = findViewById(R.id.titleText)
        fetchCards = findViewById(R.id.fetchCardsButton)
        cardTypeButton = findViewById(R.id.cardTypeButton)
        actionButton = findViewById(R.id.startButton)
        spinner = findViewById(R.id.spinner)

        calls = ApiCalls(this,db)

        cardTypeButton.setOnClickListener {
            if (this.currentCardType == SubTypeCodes.weakness) {
                this.currentCardType = SubTypeCodes.investigator
            } else {
                this.currentCardType = SubTypeCodes.weakness
            }
        }

        actionButton.setOnClickListener {
            spinner.visibility = View.VISIBLE
            cardImage.visibility = View.INVISIBLE
            titleText.visibility = View.INVISIBLE
            val card = calls.getRandomCard(currentCardType)
            val cardImage = calls.getCardImage(card.picture_id)
            this.cardImage.setImageBitmap(cardImage)
            spinner.visibility = View.INVISIBLE
            this.cardImage.visibility = View.VISIBLE
            titleText.visibility = View.VISIBLE
            titleText.text = card.name
        }

        fetchCards.setOnClickListener {
            calls.fetchCardsFromApi(CardFetchDataFields(cardImage, spinner))
        }
    }
}