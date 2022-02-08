package com.example.draftmakerhelper.models.RandomOrgModels

data class Result(
    val advisoryDelay: Int,
    val bitsLeft: Int,
    val bitsUsed: Int,
    val random: Random,
    val requestsLeft: Int
)