package com.example.myfirstapplication.components.activities.composes.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.components.activities.viewmodel.SaldoViewModel
import com.example.myfirstapplication.ui.theme.TemaPersonalizado
import org.koin.compose.koinInject

@Composable
inline fun PopUpTrocarMoeda(saldoViewModel: SaldoViewModel, crossinline onDismiss: () -> Unit) {
    val moedas = listOf("R$", "$")

    Surface(
        color = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier.padding(35.dp)
    ) {
        Column(
            Modifier
                .padding(25.dp, 12.dp, 25.dp, 25.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close dialog icon",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onDismiss() }
            )
            Spacer(Modifier.height(4.dp))
            HorizontalDivider()
            Spacer(Modifier.height(15.dp))
            Text(
                text = "Escolha uma moeda:",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight(600)
            )
            Spacer(Modifier.height(15.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp, 0.dp)
            ) {
                for (moeda in moedas) {
                    Surface(
                        color = MaterialTheme.colorScheme.inverseSurface,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(50.dp)
                    ) {
                        Box(Modifier.fillMaxSize()) {
                            Text(
                                text = moeda,
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight(700),
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.Center)
                                    .clickable {
                                        saldoViewModel.mudarMoeda(moeda)
                                    },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
inline fun previewPopUpTrocarMoeda() {
    var viewModel = SaldoViewModel(koinInject())
    TemaPersonalizado(darkTheme = true) {
        PopUpTrocarMoeda(viewModel) {}
    }
}