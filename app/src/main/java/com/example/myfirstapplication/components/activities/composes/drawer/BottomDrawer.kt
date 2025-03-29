package com.example.myfirstapplication.components.activities.composes.drawer

import android.os.Build
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalView

@RequiresApi(Build.VERSION_CODES.R)
@Composable
inline fun BottomDrawer(drawerViewModel: DrawerViewModel = koinInject()) {
    val drawerState by drawerViewModel.drawerState.collectAsState()

    if (drawerState.isVisible) {
        val view = LocalView.current
        val insets = view.rootWindowInsets

        val bottomInset = insets?.getInsets(WindowInsets.Type.systemBars())?.bottom ?: 0

        val screenHeight = view.height
        val screenWidth = view.width

        var isFolded by remember { mutableStateOf(true) }
        var contentHeight by remember { mutableIntStateOf(0) }
        val animatedOffset by animateIntAsState(
            targetValue = if (isFolded) 0 else contentHeight,
            animationSpec = tween(durationMillis = 3000, easing = LinearOutSlowInEasing),
            label = "offset"
        )

        LaunchedEffect(contentHeight) {
            if (contentHeight > 0 && isFolded) {
                isFolded = false
            }
        }

        Layout(
            modifier = Modifier.wrapContentHeight(),
            content = {
                BottomDrawerContent(
                    drawerState,
                    bottomInset
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
inline fun BottomDrawerContent(drawerState: DrawerState, bottomPadding: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp)
            )
            .padding(10.dp, 12.dp, 10.dp, bottomPadding.dp + 15.dp)
            .fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(4.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primary
        ) {}
        Spacer(modifier = Modifier.height(8.dp))
        drawerState.renderFun()
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