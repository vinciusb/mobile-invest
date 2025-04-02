package com.example.myfirstapplication.components.activities.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myfirstapplication.model.StockInfo
import com.example.myfirstapplication.repository.B3Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeBrokerViewModel(repository: B3Repository) : ViewModel() {
    private var _b3Repository : B3Repository = repository

    fun armazenaToken(stockInfo: StockInfo) {
        _b3Repository.armazenaToken(stockInfo)
    }

    fun buscaToken(token: String): StateFlow<StockInfo>? {
        return _b3Repository.buscaToken(token)
    }
}