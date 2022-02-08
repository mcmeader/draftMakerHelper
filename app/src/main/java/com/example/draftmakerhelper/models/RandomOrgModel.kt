package com.example.draftmakerhelper.models

import com.example.draftmakerhelper.models.RandomOrgModels.Result

data class RandomOrgModel(
    val id: Int,
    val jsonrpc: String,
    val result: Result
)