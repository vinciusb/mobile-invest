package com.example.myfirstapplication.components.activities.composes.home

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.myfirstapplication.components.activities.HomeBrokerActivity
import com.example.myfirstapplication.components.activities.viewmodel.HomeBrokerViewModel
import com.example.myfirstapplication.model.StockInfo
import org.koin.compose.koinInject

@Composable
inline fun InvestHomeMarketingDirecionado() {

    val cards =
        listOf(
            Triple("INTR4", "Liquidez diária", "Rendeu 93% no último mes"),
            Triple("NUBR3", "Liquidez diária", "Rendeu 33% no último mes"),
            Triple("NVDA4", "Liquidez diária", "Rendeu 13% no último mes")
        )


    Column(Modifier.padding(vertical = 17.dp)) {
        Text(
            "Oportunidades do dia",
            fontSize = 20.sp,
            fontWeight = FontWeight(700),
            color = MaterialTheme.colorScheme.primary
        )
        Row(Modifier
            .padding(vertical = 14.dp)
            .horizontalScroll(rememberScrollState())) {
            cards.forEachIndexed { idx, card ->
                OportunidadeCard(card.first, card.second, card.third)
                if (idx < cards.size - 1) Spacer(Modifier.width(10.dp))
            }
        }
    }
}


@Composable
inline fun OportunidadeCard(token: String, liquidez: String, rendimento: String) {
    val context = LocalContext.current
    var homeBrokerViewModel = koinInject<HomeBrokerViewModel>()

    Column(Modifier
        .clickable {
            val openHomeBrokerIntent = Intent(context, HomeBrokerActivity::class.java).apply {
                action = Intent.ACTION_SEND;
                putExtra(Intent.EXTRA_TEXT, token);
                type = "text/plain";
            }

            homeBrokerViewModel.armazenaToken(StockInfo.geraStockAleatoria(token, 20, 1.5))
            startActivity(context, openHomeBrokerIntent, null)
        }
        .width(190.dp)
        .height(170.dp)
        .background(MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
        .clip(RoundedCornerShape(8.dp))
        .border(
            BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
            shape = RoundedCornerShape(8.dp)
        )
        .padding(5.dp, 20.dp, 5.dp, 5.dp),
        verticalArrangement = Arrangement.Bottom) {
        Text(
            text = token,
            fontWeight = FontWeight(800),
            color = MaterialTheme.colorScheme.tertiary,
            fontSize = 20.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = liquidez,
            fontWeight = FontWeight(500),
            color = MaterialTheme.colorScheme.tertiary
        )
        Text(
            text = rendimento,
            fontWeight = FontWeight(500),
            color = MaterialTheme.colorScheme.primary
        )
    }
}