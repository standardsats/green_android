package io.crypto.feature.settings.presentation.screens.facescannerloading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import io.crypto.feature.settings.navigation.AppNavigator
import io.crypto.feature.settings.presentation.screens.facescannersuccess.FaceScannerSuccessScreen
import javax.inject.Inject

@HiltViewModel
class FaceScannerLoadScreenViewModel @Inject constructor(private val navigator: AppNavigator):ViewModel() {
    init {
        openLoadingScreen()
    }
    private fun openLoadingScreen(){
        viewModelScope.launch {
            delay(1500)
            navigator.replace(FaceScannerSuccessScreen())
        }
    }
    fun back(){
        viewModelScope.launch {
            navigator.back()
        }
    }
}