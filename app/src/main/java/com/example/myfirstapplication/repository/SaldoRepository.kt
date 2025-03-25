package com.example.myfirstapplication.repository

import com.example.myfirstapplication.model.Saldo
import com.example.myfirstapplication.model.TipoSaldo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SaldoRepository(saldo: Saldo) {
    private val _saldo = MutableStateFlow<Saldo>(saldo)
    val saldo: StateFlow<Saldo> = _saldo

    fun mudarMoeda(novaMoeda: String): Unit {
        _saldo.value = _saldo.value.copy(moeda = novaMoeda)
    }

    fun alterarVisibilidade() {
        _saldo.value = _saldo.value.copy(visivel = !_saldo.value.visivel)
    }

    fun deposita(quantia: Double, tipoSaldo: TipoSaldo): Unit {
        val novoSaldos = _saldo.value.saldos.toMutableMap()
        val quantiaAntiga = novoSaldos.getOrDefault(tipoSaldo, 0.0)
        novoSaldos[tipoSaldo] = quantiaAntiga + quantia

        _saldo.value  = _saldo.value.copy(saldos = novoSaldos)
    }

    fun saca(quantia: Double, tipoSaldo: TipoSaldo): Boolean {
        if (!_saldo.value.saldos.containsKey(tipoSaldo)) return false
        if (_saldo.value.saldos[tipoSaldo]!! < quantia) return false


        val novoSaldos = _saldo.value.saldos.toMutableMap()
        novoSaldos[tipoSaldo] = novoSaldos[tipoSaldo]!! - quantia
        _saldo.value = _saldo.value.copy(saldos = novoSaldos)

        return true
    }

}