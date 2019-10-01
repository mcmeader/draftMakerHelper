package com.example.draftmakerhelper

import java.time.LocalDateTime
import kotlin.random.Random

class ListGenerator {

    var number: Int = 0

    fun getRandomList(): List<Int> {
        val numbers: MutableList<Int> = mutableListOf()

        while (number > 0) {
            val shuffledValue = Random(LocalDateTime.now().nano)
            val randomValue = Random(LocalDateTime.now().second)
            val randomInteger = (1..number).shuffled(shuffledValue).random(randomValue)

            numbers.add(randomInteger)

            number--
        }
        return numbers
    }

    fun setNumberofEntries(number: Int) {
        this.number = number
    }
}