package com.example.myfirstapplication.model

import java.time.LocalDateTime

data class StockSegment(
    val low: Double,
    val open: Double,
    val close: Double,
    val high: Double,
    val time: LocalDateTime
) {
    fun obterDiff(): Double {
        return close - open
    }
}