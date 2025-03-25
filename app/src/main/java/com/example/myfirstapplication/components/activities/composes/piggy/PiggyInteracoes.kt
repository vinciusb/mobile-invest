package com.example.myfirstapplication.components.activities.composes.piggy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.components.activities.viewmodel.PiggyViewModel
import com.example.myfirstapplication.components.activities.viewmodel.drawer.DrawerViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
inline fun PiggyInteracoes(piggyViewModel: PiggyViewModel, drawerViewModel: DrawerViewModel = koinInject()) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            contentPadding = PaddingValues(6.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                drawerViewModel.setVisible { Text("Esse texto deve aparecer em um drawer") }
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