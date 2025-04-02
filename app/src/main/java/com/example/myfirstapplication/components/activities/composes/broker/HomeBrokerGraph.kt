package com.example.myfirstapplication.components.activities.composes.broker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.components.activities.viewmodel.HomeBrokerViewModel
import com.example.myfirstapplication.components.activities.viewmodel.SaldoViewModel
import com.example.myfirstapplication.kotlin.appModule
import com.example.myfirstapplication.ui.theme.TemaPersonalizado
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
inline fun HomeBrokerGraph(homeBrokerViewModel: HomeBrokerViewModel = koinInject()) {
    val intr = homeBrokerViewModel.buscaToken("INTR")

    val state = intr!!.collectAsState()

    Column {
        state.value.valores.forEach { valor ->
            Text(
                String.format(
                    "%.2f | %.2f | %.2f | %.2f",
                    valor.low,
                    valor.close,
                    valor.high,
                    valor.open
                )
            )
        }

    }
}