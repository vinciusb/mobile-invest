package com.example.myfirstapplication.components.activities.viewmodel.drawer

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.example.myfirstapplication.model.DrawerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DrawerViewModel(private val initState: DrawerState) : ViewModel() {

    private val _state = MutableStateFlow<DrawerState>(initState)
    var drawerState: StateFlow<DrawerState> = _state.asStateFlow();

    fun setVisible(renderFun: @Composable () -> Unit) {
        _state.value = _state.value.copy(isVisible = true, renderFun = renderFun)
    }

    fun unsetVisible() {
        _state.value = _state.value.copy(isVisible = false)
    }

}