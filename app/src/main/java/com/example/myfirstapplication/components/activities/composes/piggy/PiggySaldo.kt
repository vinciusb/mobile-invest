package com.example.myfirstapplication.components.activities.composes.piggy

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.components.activities.viewmodel.PiggyViewModel

@Composable
inline fun PiggySaldo(piggyViewModel: PiggyViewModel) {
    val saldo  by piggyViewModel.obterSaldo().collectAsState()

    Text(
        text = saldo.formatado(),
        fontSize = 32.sp,
        fontWeight = FontWeight(800),
        color = MaterialTheme.colorScheme.primary
    )
}