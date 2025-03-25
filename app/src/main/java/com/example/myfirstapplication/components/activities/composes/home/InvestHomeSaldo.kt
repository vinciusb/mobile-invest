package com.example.myfirstapplication.components.activities.composes.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.components.activities.viewmodel.SaldoViewModel
import com.example.myfirstapplication.model.TipoSaldo
import org.koin.androidx.compose.koinViewModel

@Composable
inline fun InvestHomeSaldo(viewModel: SaldoViewModel = koinViewModel()) {
    val saldo by viewModel.obterSaldo().collectAsState()

    Column(
        modifier = Modifier
            .border(2.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
            .padding(14.dp, 18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = saldo.formatado(),
                fontSize = 32.sp,
                fontWeight = FontWeight(800),
                color = MaterialTheme.colorScheme.primary
            )
            Row(modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh Icon",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(12.dp, 0.dp)
                        .size(27.dp)
                        .clickable {}
                )
                if (saldo.visivel) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = "Visibility On Icon",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(27.dp)
                            .clickable {
                                viewModel.alterarVisibilidade()
                            }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = "Visibility Off Icon",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(27.dp)
                            .clickable {
                                viewModel.alterarVisibilidade()
                            }
                    )
                }
            }
        }
        Text(
            text = "Acessar Carteira",
            fontWeight = FontWeight(600),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
