package com.example.myfirstapplication.components.activities.composes.piggy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.model.AboutSectionDTO

@Composable
inline fun PiggyAbout() {

    val secoes = listOf(
        AboutSectionDTO(
            "Guardar automaticamente",
            "Faça render todo o dinheiro que você recebe e fica parado na sua conta.",
            "#/about-1"
        ),
        AboutSectionDTO(
            "Onde estou guardando",
            "Acesse sua carteira.",
            "#/about-2"
        ),
        AboutSectionDTO(
            "Comprar novo visual",
            "Compre aqui um visual deslumbrante para seu porquinho.",
            "#/about-3"
        )
    )


    Column(horizontalAlignment = Alignment.Start) {
        Text(
            "Sobre o porquinho",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            fontWeight = FontWeight(700)
        )
        Spacer(Modifier.height(20.dp))
        Column() {
            secoes.forEachIndexed() { idx, sec ->
                PiggyAboutSection(sec)
                if (idx < secoes.size - 1) HorizontalDivider(
                    Modifier.padding(
                        top = 15.dp,
                        bottom = 8.dp
                    )
                )
            }
        }
    }
}

@Composable
inline fun PiggyAboutSection(section: AboutSectionDTO) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                section.titulo,
                color = MaterialTheme.colorScheme.tertiary,
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
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = "Go to about",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .clickable { println("Go to ${section.hyperlink}") }

        )
    }
}

@Preview
@Composable
inline fun PreviewPiggyAbout() {
    PiggyAbout()
}