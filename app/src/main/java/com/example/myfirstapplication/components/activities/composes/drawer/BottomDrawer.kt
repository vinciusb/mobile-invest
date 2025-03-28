package com.example.myfirstapplication.components.activities.composes.drawer

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

        val topInset = insets?.getInsets(WindowInsets.Type.systemBars())?.top ?: 0
        val bottomInset = insets?.getInsets(WindowInsets.Type.systemBars())?.bottom ?: 0

        val screenHeight = view.height - topInset - bottomInset
        val screenWidth = view.width

        println("h: $screenHeight w: $screenWidth")

        Layout(content = { BottomDrawerContent(drawerState) }) { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) }
            val contentHeight = placeables.first().height
            layout(screenWidth, constraints.maxHeight) {
                placeables.forEach { it.place(0, screenHeight - contentHeight) }
//                placeables.forEach { it.place(0, 1000) }
            }
        }
    }
}

@Composable
inline fun BottomDrawerContent(drawerState: DrawerState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.outline)
            .padding(10.dp, 2.dp, 10.dp, 30.dp)
            .width(2000.dp)
//            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))

    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(5.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.background
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