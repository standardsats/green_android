package io.crypto.feature.settings.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import io.crypto.feature.settings.navigation.AppNavigator
import io.crypto.feature.settings.presentation.screens.map.MapScreen
import io.crypto.feature.settings.presentation.screens.send.SendScreen
import io.crypto.feature.settings.presentation.screens.settings.SettingsScreen
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val navigator: AppNavigator):ViewModel() {

    //todo
    fun openMapScreen(){
        viewModelScope.launch {
            navigator.navigateTo(MapScreen())
        }
    }
    fun openSettingsScreen(){
        viewModelScope.launch {
            navigator.navigateTo(SettingsScreen())
        }
    }

    fun openSendScreen() {
        viewModelScope.launch {
            navigator.navigateTo(SendScreen())
        }
    }
}