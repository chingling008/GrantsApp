package com.example.grantzapp.activity

import java.util.*

internal class Randomgenerator {
    private val LETTERS = "abcdefghijklmnopqrstuvwxyz"
    private val NUMBERS = "0123456789"
    private val ALPHANUMERIC = (LETTERS + LETTERS.toUpperCase(Locale.ROOT) + NUMBERS).toCharArray()
    fun generateAlphaNumeric(length: Int): String {
        val result = StringBuilder()
        for (i in 0 until length) {
            result.append(ALPHANUMERIC[Random().nextInt(ALPHANUMERIC.size)])
        }
        return result.toString()
    }
}