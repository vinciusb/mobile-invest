package com.example.myfirstapplication.kotlin

import com.example.myfirstapplication.components.activities.viewmodel.PiggyViewModel
import com.example.myfirstapplication.components.activities.viewmodel.SaldoViewModel
import com.example.myfirstapplication.components.activities.viewmodel.drawer.DrawerViewModel
import com.example.myfirstapplication.model.DrawerState
import com.example.myfirstapplication.model.Saldo
import com.example.myfirstapplication.repository.SaldoRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { Saldo("R$", true) }
    factory { DrawerState(false) {} }
    single { SaldoRepository(get()) }
    viewModel { SaldoViewModel(get()) }
    viewModel { PiggyViewModel(get()) }
    single { DrawerViewModel(get()) }
}