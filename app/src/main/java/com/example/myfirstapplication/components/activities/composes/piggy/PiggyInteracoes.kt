package com.example.myfirstapplication.components.activities.composes.piggy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.components.activities.viewmodel.PiggyViewModel
import com.example.myfirstapplication.components.activities.viewmodel.drawer.DrawerViewModel
import com.example.myfirstapplication.kotlin.appModule
import com.example.myfirstapplication.model.Saldo
import com.example.myfirstapplication.model.TipoSaldo
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
inline fun PiggyInteracoes(
    piggyViewModel: PiggyViewModel,
    drawerViewModel: DrawerViewModel = koinInject()
) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            contentPadding = PaddingValues(6.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                drawerViewModel.enable {
                    drawerKeyboard(piggyViewModel, drawerViewModel, "Aplicar", { saldo ->
                        piggyViewModel.deposita(saldo.saldos[TipoSaldo.Piggy]!!, TipoSaldo.Piggy)
                        drawerViewModel.disable()
                    })
                }
            }) {
            Text(
                "Guardar Dinheiro",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight(600)
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(6.dp),
            onClick = {
                drawerViewModel.enable {
                    drawerKeyboard(piggyViewModel, drawerViewModel, "Sacar", { saldo ->
                        piggyViewModel.saca(saldo.saldos[TipoSaldo.Piggy]!!, TipoSaldo.Piggy)
                        drawerViewModel.disable()
                    })
                }
            }) {
            Text(
                "Resgatar",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 16.sp,
                fontWeight = FontWeight(600)
            )
        }
    }
}

@Composable
inline fun drawerKeyboard(
    piggyViewModel: PiggyViewModel,
    drawerViewModel: DrawerViewModel,
    onSuccesText: String,
    crossinline onSuccess: (Saldo) -> Unit,
    initSaldo: Saldo = koinInject<Saldo>()
) {
    var saldo = remember { mutableStateOf(initSaldo.copy()) }

    val buttonModifier = Modifier.padding(4.dp, 0.dp)

    Column {
        Text(
            text = saldo.value.formatado(),
            fontSize = 32.sp,
            fontWeight = FontWeight(800),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        (0..2).map { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                (1..3).map({ column ->
                    KeyboardButton(row * 3 + column, buttonModifier, saldo)
                })
            }
        }
        KeyboardButton(0, buttonModifier.align(Alignment.CenterHorizontally), saldo)
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Button(
                modifier = buttonModifier,
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    val newSaldos = saldo.value.saldos.toMutableMap()
                    newSaldos[TipoSaldo.Piggy] = 0.0
                    saldo.value = saldo.value.copy(saldos = newSaldos)
                }) { Text("Limpar", fontSize = 18.sp) }
            Button(
                modifier = buttonModifier,
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    onSuccess(saldo.value)
                }) {
                Text(
                    onSuccesText,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
inline fun KeyboardButton(number: Int, modifier: Modifier, saldo: MutableState<Saldo>) {
    Button(
        onClick = {
            val newSaldos = saldo.value.saldos.toMutableMap()
            newSaldos[TipoSaldo.Piggy] = newSaldos[TipoSaldo.Piggy]!! * 10 + number * 0.01
            saldo.value = saldo.value.copy(saldos = newSaldos)
        },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 30.dp)
    ) { Text("$number", fontSize = 18.sp) }
}

@Preview
@Composable
inline fun previewGuardarDinheiro() {
    val context = LocalContext.current
    KoinApplication(application = {
        androidContext(context)
        modules(appModule)
    }) {
        val piggyViewModel = PiggyViewModel(koinInject())
        val drawerViewModel = DrawerViewModel(koinInject())
        drawerKeyboard(piggyViewModel, drawerViewModel, "Aplicar", {})
    }
}