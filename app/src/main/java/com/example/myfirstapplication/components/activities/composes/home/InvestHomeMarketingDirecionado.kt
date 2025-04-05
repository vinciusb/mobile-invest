package com.example.myfirstapplication.components.activities.composes.home

import android.content.Intent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.myfirstapplication.components.activities.HomeBrokerActivity
import com.example.myfirstapplication.components.activities.viewmodel.HomeBrokerViewModel
import com.example.myfirstapplication.model.StockInfo
import org.koin.compose.koinInject

@Composable
inline fun InvestHomeMarketingDirecionado() {
    val context = LocalContext.current
    var homeBrokerViewModel = koinInject<HomeBrokerViewModel>()

    Row {
        Surface(onClick = {
            val openHomeBrokerIntent = Intent(context, HomeBrokerActivity::class.java).apply {
                action = Intent.ACTION_SEND;
                putExtra(Intent.EXTRA_TEXT, "INTR");
                type = "text/plain";
            }

            homeBrokerViewModel.armazenaToken(StockInfo.geraStockAleatoria("INTR", 20, 1.5))
            startActivity(context, openHomeBrokerIntent, null)
        }) {
            Text("TODO HOME BROKER", modifier = Modifier.padding(20.dp))
        }
    }
}