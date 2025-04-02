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

@Composable
inline fun InvestHomeMarketingDirecionado() {
    val context = LocalContext.current
    Row {
        Surface(onClick = {
            val openHomeBrokerIntent = Intent(context, HomeBrokerActivity::class.java).apply {
                action = Intent.ACTION_SEND;
                putExtra(Intent.EXTRA_TEXT, "INTR");
                type = "text/plain";
            }
            startActivity(context, openHomeBrokerIntent, null)
        }) {
            Text("TODO HOME BROKER", modifier = Modifier.padding(20.dp))
        }
    }
}