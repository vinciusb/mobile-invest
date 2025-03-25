package com.example.myfirstapplication.components.activities.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myfirstapplication.model.Saldo
import com.example.myfirstapplication.repository.SaldoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PiggyViewModel(saldoRepository: SaldoRepository) : ViewModel() {

    private val _saldoRepository: SaldoRepository = saldoRepository

    fun alterarVisibilidade() {
        _saldoRepository.alterarVisibilidade()
    }

    fun deposita(quantia: Double): Unit {
//        _saldoRepository.deposita(quantia, tipoSaldo)
    }

    fun saca(quantia: Double): Boolean {
//        return _saldoRepository.saca(quantia)
        return true
    }

    fun obterSaldo() : StateFlow<Saldo> {
        return _saldoRepository.saldo
    }
}