package com.example.myfirstapplication.components.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.components.activities.composes.broker.HomeBrokerGraph
import com.example.myfirstapplication.components.activities.composes.piggy.PiggyAboutSection
import com.example.myfirstapplication.components.activities.viewmodel.HomeBrokerViewModel
import com.example.myfirstapplication.kotlin.appModule
import com.example.myfirstapplication.model.AboutSectionDTO
import com.example.myfirstapplication.model.StockInfo
import com.example.myfirstapplication.ui.theme.TemaPersonalizado
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import kotlin.math.abs

class HomeBrokerActivity() : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tokenAcao = intent.getStringExtra(Intent.EXTRA_TEXT) ?: "INTR"

        setContent {
            TemaPersonalizado(darkTheme = true) {
                Surface(
                    color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) { paddingValues ->
                        HomeBrokerHome(paddingValues, tokenAcao)
                    }
                }
            }
        }
    }

    @Composable
    private fun HomeBrokerHome(
        paddingValues: PaddingValues = PaddingValues(20.dp),
        token: String,
        homeBrokerViewModel: HomeBrokerViewModel = koinInject()
    ) {
        val stockInfo by homeBrokerViewModel.buscaToken(token)!!.collectAsState()

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            InfoHeader(token, stockInfo)
            Box(
                Modifier
                    .height(500.dp)
                    .padding(17.dp, 5.dp)
            ) {
                HomeBrokerGraph()
            }
            BrokerBenefits()
        }
    }

    @Composable
    fun InfoHeader(token: String, stockInfo: StockInfo) {
        val context = LocalContext.current as? Activity

        Row(
            Modifier
                .padding(17.dp, 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go back",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .size(37.dp)
                    .clickable { context?.finish() })
            Text(stockInfo.nome, fontSize = 20.sp, fontWeight = FontWeight(700))
            Icon(imageVector = Icons.Default.Share,
                contentDescription = "Go back",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .size(37.dp)
                    .clickable { context?.finish() })
        }
        Row(
            Modifier
                .padding(17.dp, 5.dp, 17.dp, 17.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                Modifier.padding(end = 15.dp),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.outline
            ) {
                Text(
                    "C",
                    Modifier.padding(14.dp, 10.dp),
                    fontWeight = FontWeight(800),
                    fontSize = 20.sp
                )
            }
            Text(
                token, Modifier.padding(10.dp, 0.dp), fontWeight = FontWeight(800), fontSize = 25.sp
            )
            Icon(imageVector = Icons.Default.Favorite,
                contentDescription = "Favoritar",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { })
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                val diff = stockInfo.valores.last().obterDiff()
                val percentageDiff = diff / stockInfo.valores.last().open
                val color = if (diff > 0) Color(6, 179, 86, 255) else Color(243, 116, 92, 255)

                Text(
                    "R\$ ${String.format("%.2f", stockInfo.valores.last().close)}",
                    fontWeight = FontWeight(800),
                    fontSize = 17.sp,
                )
                Text(
                    "${if (diff < 0) "-" else "+"}R\$ ${
                        String.format(
                            "%.2f", abs(diff)
                        )
                    } (${String.format("%.2f", abs(percentageDiff))}%)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight(800),
                    color = color
                )
            }

        }
        Surface(
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(17.dp, 17.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .height(14.dp)
                        .width(14.dp),
                    color = Color.Red,
                    shape = RoundedCornerShape(10.dp)
                ) { }
                Text(
                    "Mercado Fechado",
                    fontSize = 13.sp,
                    fontWeight = FontWeight(600),
                    modifier = Modifier.padding(horizontal = 5.dp),
                    color = MaterialTheme.colorScheme.tertiary
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Help,
                    contentDescription = "Help about market",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(17.dp)
                )
                Text(
                    "Abre na quinta às 10:00",
                    Modifier.weight(1f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight(600),
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }

    @Composable
    fun BrokerBenefits() {
        val secoes = listOf(
            AboutSectionDTO(
                "Carteira remunerada",
                "Alugue suas ações e tenha uma renda extra.",
                "#/about-1"
            ), AboutSectionDTO(
                "InvestPro",
                "Ferramentas e conteúdos exclusivos para potencializar seus rendimentos.",
                "#/about-2"
            ), AboutSectionDTO(
                "Meu Porquinho",
                "Reinvista seus aluguéis de FIIs, dividendos e rendimentos recebidos de ativos negociados na bolsa.",
                "#/about-3"
            )
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Surface(
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            ) {}
            Column(Modifier.padding(17.dp, 16.dp), horizontalAlignment = Alignment.Start) {
                Text(
                    "Benefícios para você",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700)
                )
                Spacer(Modifier.height(20.dp))
                Column() {
                    secoes.forEachIndexed() { idx, sec ->
                        BrokerAbout(sec)
                        if (idx < secoes.size - 1) HorizontalDivider(
                            Modifier.padding(
                                top = 15.dp, bottom = 8.dp
                            )
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun BrokerAbout(section: AboutSectionDTO) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    section.titulo,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight(700),
                    fontSize = 16.sp
                )
                Text(
                    section.conteudo,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(end = 30.dp),
                    softWrap = true,
                    fontWeight = FontWeight(400),
                    fontSize = 14.sp,
                    lineHeight = 12.sp
                )
            }
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Go to about",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable { println("Go to ${section.hyperlink}") }

            )
        }
    }

    @Preview
    @Composable
    fun previewableHomeBroker() {
        val context = LocalContext.current
        KoinApplication(application = {
            androidContext(context)
            modules(appModule)
        }) {
            val homeBrokerViewModel = HomeBrokerViewModel(koinInject())
            homeBrokerViewModel.armazenaToken(StockInfo.geraStockAleatoria("INTR", 20, 1.5))
            TemaPersonalizado(darkTheme = true) {
                Surface(
                    color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) { paddingValues ->
                        HomeBrokerHome(paddingValues, "INTR", homeBrokerViewModel)
                    }
                }
            }
        }
    }
}