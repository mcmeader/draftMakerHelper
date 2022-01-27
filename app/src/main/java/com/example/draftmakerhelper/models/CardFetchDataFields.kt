package com.example.draftmakerhelper.models

import android.content.Context
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

data class CardFetchDataFields(
    val option: CardFetchOptions,
    val context: Context,
    val coreSetOnly:Boolean,
    val cardImage: ImageView,
    val staticText: TextView,
    val progressBar: ProgressBar,
}
