package com.example.myfirstapplication.components.activities.composes.home

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import com.example.myfirstapplication.components.activities.PiggyHomeActivity
import com.example.myfirstapplication.components.activities.viewmodel.SaldoViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
inline fun InvestHomeFastActions(saldoViewModel: SaldoViewModel = koinViewModel()) {
    var showDialog by remember { mutableStateOf(false) }
    var dialogCompose by remember { mutableStateOf<@Composable () -> Unit>({}) }
    val context = LocalContext.current

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) { dialogCompose() }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
    ) {
        FastAction(
            "Trocar Moeda",
            Icons.Filled.CurrencyExchange,
            MaterialTheme.colorScheme.inverseSurface,
            MaterialTheme.colorScheme.secondary
        ) {
            showDialog = true
            dialogCompose = { PopUpTrocarMoeda(saldoViewModel) { showDialog = false } }
        }
        FastAction(
            "Porquinho",
            Icons.Filled.Savings,
            MaterialTheme.colorScheme.inverseSurface,
            MaterialTheme.colorScheme.secondary
        ) {
            val openPiggyIntent = Intent(context, PiggyHomeActivity::class.java).apply {
                action = Intent.ACTION_SEND;
                putExtra(Intent.EXTRA_TEXT, "Olá, eu sou o seu porquinho!");
                type = "text/plain";
            }
            startActivity(context, openPiggyIntent, null)
        }
        FastAction(
            "Mais",
            Icons.Filled.MoreHoriz,
            MaterialTheme.colorScheme.inverseSurface,
            MaterialTheme.colorScheme.secondary
        ) {}
    }
}

@Composable
inline fun FastAction(
    title: String,
    icon: ImageVector,
    backgroundColor: Color,
    fillColor: Color,
    crossinline onClick:  () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp, 8.dp)
    ) {
        Surface(
            color = backgroundColor,
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(imageVector = icon,
                contentDescription = title,
                tint = fillColor,
                modifier = Modifier
                    .size(65.dp)
                    .clickable {
                        onClick()
                    }
                    .align(Alignment.CenterHorizontally)
                    .padding(17.dp)
            )
        }
        Text(text = title, fontSize = 12.sp, fontWeight = FontWeight(600))
    }
}