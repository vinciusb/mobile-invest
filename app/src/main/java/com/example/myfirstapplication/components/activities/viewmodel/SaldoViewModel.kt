package com.example.myfirstapplication.components.activities.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.myfirstapplication.model.Saldo
import com.example.myfirstapplication.model.TipoSaldo
import com.example.myfirstapplication.repository.SaldoRepository
import kotlinx.coroutines.flow.StateFlow

open class SaldoViewModel(saldoRepository: SaldoRepository) : ViewModel() {

    private val _saldoRepository : SaldoRepository = saldoRepository

    fun mudarMoeda(novaMoeda: String): Unit {
        _saldoRepository.mudarMoeda(novaMoeda)
    }

    fun alterarVisibilidade() {
        _saldoRepository.alterarVisibilidade()
    }

    fun deposita(quantia: Double, tipoSaldo: TipoSaldo): Unit {
        _saldoRepository.deposita(quantia, tipoSaldo)
    }

    fun saca(quantia: Double, tipoSaldo: TipoSaldo): Boolean {
        return _saldoRepository.saca(quantia, tipoSaldo)
    }

    fun obterSaldo() : StateFlow<Saldo> {
        return _saldoRepository.saldo
    }

}