package com.example.myfirstapplication.model

import java.time.LocalDateTime
import kotlin.random.Random

data class StockInfo(val nome: String, val valores: List<StockSegment>) {
    companion object {
        private fun generateRandomSegment(
            base: Double,
            trend: Double,
            randomness: Double,
            hour: LocalDateTime
        ): StockSegment {
            val open = base + trend + Random.nextDouble(-randomness, randomness)
            val close = base + trend + Random.nextDouble(-randomness, randomness)
            val low = minOf(open, close) - Random.nextDouble(0.0, randomness / 2)
            val high = maxOf(open, close) + Random.nextDouble(0.0, randomness / 2)
            return StockSegment(low, open, close, high, hour)
        }

        fun geraStockAleatoria(nome: String, numeroSegmentos: Int, tendencia: Double): StockInfo {
            val base = Random.nextDouble(10.0, 100.0)
            val startTime = LocalDateTime.of(2025, 4, 6, 0, 6, 10, 15)
            val timeStep = Random.nextDouble(0.0, 20.0).toLong()
            val valores = List(numeroSegmentos) { i ->
                generateRandomSegment(
                    base,
                    i * tendencia,
                    10.0,
                    startTime.plusMinutes(i * timeStep)
                )
            }
            return StockInfo(nome, valores)
        }
    }

    fun calcularMinimo(): Double {
        return valores
            .stream()
            .map { seg -> seg.low }
            .min(Double::compareTo)
            .get()
    }

    fun calcularMaximo(): Double {
        return valores
            .stream()
            .map { seg -> seg.high }
            .max(Double::compareTo)
            .get()
    }
}