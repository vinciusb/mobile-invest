package com.example.myfirstapplication.model

import java.util.stream.Collectors

data class Saldo(
    val moeda: String,
    val visivel: Boolean,
    var saldos: MutableMap<TipoSaldo, Double> = mutableMapOf()
) {
    init {
        for (tipoSaldo in TipoSaldo.values()) {
            saldos[tipoSaldo] = saldos.getOrDefault(tipoSaldo, 0.0)
        }
    }

    fun formatado(tipoSaldo: TipoSaldo? = null): String {
        if (!visivel) return "$moeda ---"

        val formattedValue = if (tipoSaldo == null) {
            val total = saldos.values.sum()
            String.format("%.2f", total)
        } else {
            String.format("%.2f", saldos[tipoSaldo] ?: 0.0)
        }

        return "$moeda $formattedValue"
    }
}