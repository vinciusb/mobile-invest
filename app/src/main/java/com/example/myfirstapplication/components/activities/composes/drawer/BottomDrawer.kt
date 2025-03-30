package com.example.myfirstapplication.components.activities.composes.drawer

import android.os.Build
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.components.activities.viewmodel.drawer.DrawerViewModel
import com.example.myfirstapplication.kotlin.appModule
import com.example.myfirstapplication.model.DrawerState
import com.example.myfirstapplication.ui.theme.TemaPersonalizado
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.IntOffset
import kotlin.math.max
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.R)
@Composable
inline fun BottomDrawer(drawerViewModel: DrawerViewModel = koinInject()) {
    val drawerState by drawerViewModel.drawerState.collectAsState()
    var isShowing by remember { mutableStateOf(false) }

    LaunchedEffect(drawerState.isEnabled) {
        if (drawerState.isEnabled) {
            isShowing = true
        }
    }

    if (isShowing) {
        val view = LocalView.current
        val bottomInset =
            view.rootWindowInsets?.getInsets(WindowInsets.Type.systemBars())?.bottom ?: 0
        val screenHeight = view.height
        val screenWidth = view.width

        var isFolded by remember { mutableStateOf(true) }
        var contentHeight by remember { mutableIntStateOf(0) }
        val animatedOffset by animateIntAsState(
            targetValue = if (isFolded) 0 else contentHeight,
            animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
            label = "offset",
            finishedListener = { value ->
                if (value == 0) {
                    drawerViewModel.disable()
                    isShowing = false
                }
            }
        )

        var isDragging by remember { mutableStateOf(false) }
        var dragOffset by remember { mutableFloatStateOf(0.0f) }
        val animatedDragOffset by animateFloatAsState(
            targetValue = if (isDragging) dragOffset else 0.0f,
            animationSpec = tween(durationMillis = 400, easing = LinearOutSlowInEasing),
            label = "offset"
        )

        LaunchedEffect(contentHeight) {
            if (contentHeight > 0 && isFolded) {
                isFolded = false
            }
        }

        LaunchedEffect(drawerState.isEnabled) {
            if (!drawerState.isEnabled) {
                isFolded = true
            }
        }

        Layout(
            modifier = Modifier
                .wrapContentHeight()
                .offset {
                    val currentOffset = if (isDragging) dragOffset else animatedDragOffset
                    IntOffset(0, currentOffset.roundToInt())
                },
            content = {
                BottomDrawerContent(
                    drawerState.renderFun,
                    bottomInset,
                    { delta -> dragOffset = max(dragOffset + delta, 0f) },
                    {
                        isDragging = true
                    },
                    {
                        if (dragOffset >= 0.6 * contentHeight) {
                            isFolded = true
                        } else {
                            dragOffset = 0f
                            isDragging = false
                        }
                    }
                )
            }) { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) }
            contentHeight = placeables.first().height

            layout(screenWidth, constraints.maxHeight) {
                placeables.forEach { it.place(0, screenHeight - animatedOffset) }
            }
        }
    }
}

@Composable
inline fun BottomDrawerContent(
    content: @Composable () -> Unit,
    bottomPadding: Int,
    crossinline updateOffset: (Float) -> Unit,
    crossinline onDragStart: () -> Unit,
    crossinline onDragEnd: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp)
            )
            .padding(10.dp, 12.dp, 10.dp, bottomPadding.pxToDp())
            .fillMaxWidth(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(4.dp)
                .draggable(orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta -> updateOffset(delta) },
                    onDragStarted = { onDragStart() },
                    onDragStopped = { onDragEnd() }
                ),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primary
        ) {}
        Spacer(modifier = Modifier.height(16.dp))
        content()
    }
}

@Composable
@Preview
inline fun BottomDrawerPreview() {
    val context = LocalContext.current
    KoinApplication(application = {
        androidContext(context)
        modules(appModule)
    }) {
        val previewDrawerState = DrawerState(true)
        @Composable {
            PreviewContent()
        }
        val drawerViewModel = DrawerViewModel(previewDrawerState)
        TemaPersonalizado(darkTheme = true) {
            BottomDrawer(drawerViewModel)
        }
    }
}

@Composable
inline fun PreviewContent() {
    Column {
        Text("Linha 1")
        Text("Linha 2")
        Text("Linha 3")
        Text("Linha 4")
    }
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }