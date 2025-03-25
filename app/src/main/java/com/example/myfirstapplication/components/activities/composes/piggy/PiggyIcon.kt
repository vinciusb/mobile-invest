package com.example.myfirstapplication.components.activities.composes.piggy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.example.myfirstapplication.R
import com.example.myfirstapplication.components.activities.viewmodel.PiggyViewModel
import kotlinx.coroutines.delay

@Composable
inline fun PiggyIcon(mensagem: String, piggyViewModel: PiggyViewModel) {
    var indexAmostragem by remember { mutableIntStateOf(-1) }

    LaunchedEffect(mensagem) {
        delay(1500L)
        while (indexAmostragem < mensagem.length -1 ) {
            delay(140L)
            indexAmostragem++
        }
//        indexAmostragem++
    }
    
    val showedMessage = mensagem.substring(0..indexAmostragem)

    Box(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(50),
                border = BorderStroke(3.dp, MaterialTheme.colorScheme.secondary),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(200F)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.`piggy_skin_icon`),
                    contentDescription = "Refresh Icon",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(35.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.`main_piggy`),
                contentDescription = "Imagem do porquinho",
                modifier = Modifier.align(Alignment.Center)
            )
        }
        FloatingTextBox(showedMessage, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun FloatingTextBox(text: String, modifier: Modifier) {
    val borderColor = MaterialTheme.colorScheme.outline

    Box(modifier) {
        Column(Modifier.padding(20.dp)) {
            Box(
                modifier = Modifier
                    .drawWithCache {
                        val roundedPolygon = RoundedPolygon(
                            numVertices = 3,
                            radius = size.minDimension / 2,
                            centerX = size.width / 2,
                            centerY = size.height / 2,
                            rounding = CornerRounding(
                                size.minDimension / 10f, smoothing = 0.1f
                            )
                        )
                        val roundedPolygonPath = roundedPolygon.toPath().asComposePath()
                        onDrawBehind {
                            withTransform({
                                rotate(270f, pivot = Offset(size.width / 2, size.height / 2))
                                translate(-13f, 0f)
                            }) {
                                drawPath(roundedPolygonPath, color = borderColor)
                            }
                        }
                    }
                    .size(18.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(borderColor)
                    .border(2.dp, borderColor, RoundedCornerShape(8.dp))
                    .then(if (text == "") Modifier.padding(10.dp, 0.dp) else Modifier)

            ) {
                Text(
                    text,
                    modifier = Modifier.padding(7.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }
    }
}
