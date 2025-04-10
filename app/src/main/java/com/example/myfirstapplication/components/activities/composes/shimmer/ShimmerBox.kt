package com.example.myfirstapplication.components.activities.composes.shimmer

import android.content.Context
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
inline fun ShimmerBox(width: Int, height: Int) {
    val context = LocalContext.current

    val transition = rememberInfiniteTransition(label = "");

    val pxWidth = width.dpToPx(context)
    val pxHeight = height.dpToPx(context)

    val animationOffset by transition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.1f),
            Color.White.copy(alpha = 0.05f),
            Color.White.copy(alpha = 0.0f),
            Color.White.copy(alpha = 0.05f),
            Color.White.copy(alpha = 0.1f)
        ),
        start = Offset(pxWidth * animationOffset, pxHeight * animationOffset),
        end = Offset(pxWidth * (1f + animationOffset), pxHeight * (1f + animationOffset)),
    )

    Box(
        Modifier
            .width(width.dp)
            .height(height.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(brush)
    ) { }
}

fun Int.dpToPx(context: Context): Float = (this * context.resources.displayMetrics.density)


@Preview
@Composable
inline fun previewShimmers() {
    Row(
        Modifier
            .padding(10.dp)
            .background(Color.Black)
    ) {
        Column {
            ShimmerBox(20, 20)
        }
        Spacer(Modifier.width(4.dp))
        Column {
            (1..5).forEach() { i ->
                ShimmerBox(100, 50)
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}