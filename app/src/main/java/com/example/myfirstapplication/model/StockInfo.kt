package com.example.myfirstapplication.model

import kotlin.random.Random

data class StockInfo(val nome: String, val valores: List<StockSegment>) {
    companion object {
        private fun generateRandomSegment(
            base: Double,
            trend: Double,
            randomness: Double
        ): StockSegment {
            val open = base + trend + Random.nextDouble(-randomness, randomness)
            val close = base + trend + Random.nextDouble(-randomness, randomness)
            val low = minOf(open, close) - Random.nextDouble(-randomness / 2, randomness / 2)
            val high = maxOf(open, close) + Random.nextDouble(-randomness / 2, randomness / 2)
            return StockSegment(low, open, close, high)
        }

        fun geraStockAleatoria(nome: String, numeroSegmentos: Int, tendencia: Double) : StockInfo {
            val base = Random.nextDouble(10.0, 100.0)
            val valores = List(numeroSegmentos) { i ->
                generateRandomSegment(
                    base,
                    i * tendencia,
                    10.0
                )
            }
            return StockInfo(nome, valores)
        }
    }
}