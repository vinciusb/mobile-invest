package com.example.myfirstapplication.components.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.components.activities.composes.broker.HomeBrokerGraph
import com.example.myfirstapplication.components.activities.viewmodel.HomeBrokerViewModel
import com.example.myfirstapplication.kotlin.appModule
import com.example.myfirstapplication.model.StockInfo
import com.example.myfirstapplication.ui.theme.TemaPersonalizado
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

class HomeBrokerActivity() : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tokenAcao = intent.getStringExtra(Intent.EXTRA_TEXT) ?: "INTR"


        setContent {
            TemaPersonalizado(darkTheme = true) {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(18.dp)
                    ) { paddingValues ->
                        HomeBrokerHome(paddingValues)
                    }
                }
            }
        }
    }

    @Composable
    private fun HomeBrokerHome(
        paddingValues: PaddingValues = PaddingValues(20.dp),
        homeBrokerViewModel: HomeBrokerViewModel = koinInject()
    ) {
        Box(Modifier.height(500.dp)) {
            HomeBrokerGraph()
        }
    }

    @Preview
    @Composable
    fun previewableHomeBroker() {
        val context = LocalContext.current
        KoinApplication(application = {
            androidContext(context)
            modules(appModule)
        }) {
            val homeBrokerViewModel = HomeBrokerViewModel(koinInject())
            homeBrokerViewModel.armazenaToken(StockInfo.geraStockAleatoria("INTR", 20, 1.5))
            TemaPersonalizado(darkTheme = true) {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(18.dp)
                    ) { paddingValues ->
                        HomeBrokerHome(paddingValues, homeBrokerViewModel)
                    }
                }
            }
        }
    }
}