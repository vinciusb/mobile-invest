package com.example.myfirstapplication.components.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import com.example.myfirstapplication.components.activities.composes.home.InvestHomeFastActions
import com.example.myfirstapplication.components.activities.composes.home.InvestHomeSaldo
import com.example.myfirstapplication.components.activities.viewmodel.SaldoViewModel
import com.example.myfirstapplication.kotlin.appModule
import com.example.myfirstapplication.ui.theme.TemaPersonalizado
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

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
                        HomeInvestimentos(paddingValues)
                    }
                }
            }
        }
    }

    @Composable
    fun HomeInvestimentos(
        paddingValues: PaddingValues = PaddingValues(20.dp),
        saldoViewModel: SaldoViewModel = koinViewModel()
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            InvestHomeSaldo()
            Spacer(Modifier.height(30.dp))
            InvestHomeFastActions()
//            InvestHomeMarketingDirecionado()
        }
    }

    @Preview
    @Composable
    fun previewableHomeInvestimentos() {
        val context = LocalContext.current
        KoinApplication(application = {
            androidContext(context)
            modules(appModule)
        }) {
            val saldoViewModel = SaldoViewModel(koinInject())
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
                        HomeInvestimentos(paddingValues, saldoViewModel)
                    }
                }
            }
        }
    }
}