package com.example.myfirstapplication.repository

import com.example.myfirstapplication.model.StockInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.ArrayList

class B3Repository() {
    private val _stocksInfo = ArrayList<MutableStateFlow<StockInfo>>()

    fun armazenaToken(stockInfo: StockInfo) {
        val stockEncontrada = _stocksInfo.find { stock -> stock.value.nome == stockInfo.nome }
        if (stockEncontrada == null) {
            _stocksInfo.add(MutableStateFlow(stockInfo))
        } else {
            stockEncontrada.value = stockInfo
        }
    }

    fun buscaToken(token: String): StateFlow<StockInfo>? {
        return _stocksInfo.find { stock -> stock.value.nome == token }
    }

}