package com.example.myfirstapplication.components.activities.composes.broker

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseOutSine
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.myfirstapplication.components.activities.composes.drawer.pxToDp
import com.example.myfirstapplication.components.activities.viewmodel.HomeBrokerViewModel
import com.example.myfirstapplication.model.StockSegment
import kotlinx.coroutines.delay
import org.koin.compose.koinInject
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
inline fun HomeBrokerGraph(homeBrokerViewModel: HomeBrokerViewModel = koinInject()) {
    val intr = homeBrokerViewModel.buscaToken("INTR")

    val stockInfo by intr!!.collectAsState()
    val minimo by remember { mutableDoubleStateOf(stockInfo.calcularMinimo()) }
    val maximo by remember { mutableDoubleStateOf(stockInfo.calcularMaximo()) }
    val tempoInicio by remember { mutableStateOf(stockInfo.valores.first().time) }
    val tempoFim by remember { mutableStateOf(stockInfo.valores.last().time) }

    var animationStarted =
        remember(stockInfo.valores.size) {
            List(stockInfo.valores.size) {
                mutableStateOf(false)
            }
        }

    LaunchedEffect(intr) {
        var idx = 0
        while (idx < stockInfo.valores.size) {
            animationStarted[idx].value = true
            delay(40)
            idx++
        }
    }

    Column {
        Row {
            Column(Modifier.weight(1f)) {
                Row(Modifier.weight(1f)) {
                    stockInfo.valores.forEachIndexed() { idx, segment ->
                        CandlePoint(
                            segment,
                            minimo,
                            maximo,
                            animationStarted[idx].value,
                            Modifier.weight(1f)
                        )
                    }
                }
                XAxisTicks(tempoInicio, tempoFim, 3)
            }
            YAxisTicks(minimo, maximo, 5)
        }
    }
}

@Composable
inline fun CandlePoint(
    point: StockSegment,
    min: Double,
    max: Double,
    isShowing: Boolean,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(2.dp, 0.dp)
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            val absoluteDiff = max - min
            val absoluteHigh = constraints.maxHeight.toFloat()
            val baseOffset = absoluteHigh * (max - point.high) / absoluteDiff

            val roseUp = point.close > point.open
            val color = if (roseUp) Color(6, 179, 86, 255) else Color(243, 116, 92, 255)

            var upperMark: Double
            var lowerMark: Double

            if (roseUp) {
                upperMark = point.close
                lowerMark = point.open
            } else {
                upperMark = point.open
                lowerMark = point.close
            }

            val firstThirdHeight = absoluteHigh * (point.high - upperMark) / absoluteDiff
            val seccondThirdHeight = absoluteHigh * (upperMark - lowerMark) / absoluteDiff
            val lastThirdHeigt = absoluteHigh * (lowerMark - point.low) / absoluteDiff


            val animatedAlpha by animateFloatAsState(
                targetValue = if (isShowing) 1.0f else 0.0f,
                animationSpec = tween(durationMillis = 1500, easing = EaseOutSine),
            )
            val animatedColor by animateColorAsState(
                targetValue = if (isShowing) color else Color.Gray,
                animationSpec = tween(durationMillis = 3000, easing = EaseOutSine),
            )

            Surface(
                color = animatedColor,
                modifier = Modifier
                    .width(2.dp)
                    .height(firstThirdHeight.toInt().pxToDp())
                    .offset(y = baseOffset.toInt().pxToDp())
                    .alpha(animatedAlpha)
            ) { }
            Surface(
                color = animatedColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(seccondThirdHeight.toInt().pxToDp())
                    .offset(y = baseOffset.toInt().pxToDp() + firstThirdHeight.toInt().pxToDp())
                    .alpha(animatedAlpha)
            ) { }
            Surface(
                color = animatedColor,
                modifier = Modifier
                    .width(2.dp)
                    .height(lastThirdHeigt.toInt().pxToDp())
                    .offset(
                        y = baseOffset.toInt().pxToDp() + firstThirdHeight.toInt()
                            .pxToDp() + seccondThirdHeight.toInt().pxToDp()
                    )
                    .alpha(animatedAlpha)
            ) { }
        }
    }
}

@Composable
inline fun YAxisTicks(min: Double, max: Double, interval: Int) {
    val CAPTION_SIZE = 14.sp
    val FONT_WEIGHT = FontWeight(700)
    val stepSize = (max - min) / interval
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 5.dp, bottom = 20.dp)
    ) {
        var curTick = max
        while (curTick >= min) {
            Text(
                String.format("%.2f", curTick),
                fontSize = CAPTION_SIZE,
                fontWeight = FONT_WEIGHT,
                color = MaterialTheme.colorScheme.tertiary
            )
            curTick -= stepSize
        }
    }
}

@Composable
inline fun XAxisTicks(min: LocalDateTime, max: LocalDateTime, interval: Int) {
    val CAPTION_SIZE = 14.sp
    val FONT_WEIGHT = FontWeight(700)

    val totalDuration = Duration.between(min, max)
    val stepSize = totalDuration.dividedBy(interval.toLong()) // Duration per tick
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(20.dp)
            .fillMaxWidth()
    ) {
        var curTick = min
        for (i in 0..interval) {
            Text(
                text = curTick.format(formatter),
                fontSize = CAPTION_SIZE,
                fontWeight = FONT_WEIGHT,
                color = MaterialTheme.colorScheme.tertiary
            )
            curTick = curTick.plus(stepSize)
        }
    }
}
