package com.example.myfirstapplication.components.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.components.activities.composes.drawer.BottomDrawer
import com.example.myfirstapplication.components.activities.composes.piggy.PiggyAbout
import com.example.myfirstapplication.components.activities.composes.piggy.PiggyIcon
import com.example.myfirstapplication.components.activities.composes.piggy.PiggyInteracoes
import com.example.myfirstapplication.components.activities.composes.piggy.PiggySaldo
import com.example.myfirstapplication.components.activities.viewmodel.PiggyViewModel
import com.example.myfirstapplication.components.activities.viewmodel.drawer.DrawerViewModel
import com.example.myfirstapplication.kotlin.appModule
import com.example.myfirstapplication.model.DrawerState
import com.example.myfirstapplication.ui.theme.TemaPersonalizado
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

class PiggyHomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val mensagemMotorSegmentacao =
            intent.getStringExtra(Intent.EXTRA_TEXT) ?: "Olá, eu sou o seu porquinho!"

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
                        PiggyHome(paddingValues, mensagemMotorSegmentacao)
                    }
                }
            }
        }
    }

    @Composable
    fun PiggyHome(
        paddingValues: PaddingValues = PaddingValues(20.dp),
        mensagemMotorSegmentacao: String,
        piggyViewModel: PiggyViewModel = koinViewModel(),
        drawerViewModel: DrawerViewModel = koinInject()
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PiggyIcon(mensagemMotorSegmentacao, piggyViewModel)
            Spacer(Modifier.height(30.dp))
            PiggySaldo(piggyViewModel)
            Spacer(Modifier.height(12.dp))
            PiggyInteracoes(piggyViewModel)
            Spacer(Modifier.height(30.dp))
            PiggyAbout()
        }
        BottomDrawer(drawerViewModel)
    }

    @Preview
    @Composable
    fun previewablPiggyHome() {
        val context = LocalContext.current
        KoinApplication(application = {
            androidContext(context)
            modules(appModule)
        }) {
            val piggyViewModel = PiggyViewModel(koinInject())
            val drawerViewModel = DrawerViewModel(DrawerState(true)
            @Composable
            {
                Text("Drawer default de teste")
            })
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
                        PiggyHome(
                            paddingValues,
                            "Olá, eu sou o seu porquinho!",
                            piggyViewModel,
                            drawerViewModel
                        )
                    }
                }
            }
        }
    }
}