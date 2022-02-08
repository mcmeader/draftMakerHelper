package com.example.draftmakerhelper.models.CardResponseModels

data class DeckOption(
    val faction: List<String>,
    val level: Level,
    val limit: Int,
    val trait: List<String>
)