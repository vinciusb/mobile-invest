package com.example.myfirstapplication.model

import androidx.compose.runtime.Composable

data class DrawerState(val isVisible: Boolean, val renderFun : @Composable () -> Unit) {

}