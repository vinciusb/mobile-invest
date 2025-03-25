package com.example.myfirstapplication.components.activities.composes.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.components.activities.viewmodel.PiggyViewModel
import com.example.myfirstapplication.components.activities.viewmodel.drawer.DrawerViewModel
import com.example.myfirstapplication.kotlin.appModule
import com.example.myfirstapplication.model.DrawerState
import com.example.myfirstapplication.ui.theme.TemaPersonalizado
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout

@Composable
inline fun BottomDrawer(drawerViewModel: DrawerViewModel = koinInject()) {
    Layout(content = { BottomDrawerContent(drawerViewModel) }) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { it.placeRelative(100, 2500) } // Custom absolute positioning
        }
    }
}

@Composable
inline fun BottomDrawerContent(drawerViewModel: DrawerViewModel) {
    val drawerState by drawerViewModel.drawerState.collectAsState()

    if (drawerState.isVisible) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.outline)
                .padding(10.dp, 2.dp)
                .fillMaxWidth()
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