package io.crypto.feature.settings.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import io.crypto.feature.settings.navigation.AppNavigator
import io.crypto.feature.settings.presentation.screens.enviorment.EnvironmentScreen
import io.crypto.feature.settings.presentation.screens.pin.PinCodeScreen
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(private val navigator: AppNavigator):ViewModel() {
    fun backToHome(){
        viewModelScope.launch {
            navigator.back()
        }
    }
    fun openEnvironmentScreen(){
        viewModelScope.launch {
            navigator.navigateTo(EnvironmentScreen())
        }
    }
    fun openChangePinScreen(){
        viewModelScope.launch {
            navigator.navigateTo(PinCodeScreen())
        }
    }
}