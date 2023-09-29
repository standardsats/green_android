package io.crypto.feature.settings.presentation.screens.facescanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import io.crypto.feature.settings.navigation.AppNavigator
import io.crypto.feature.settings.presentation.screens.facescannerloading.FaceScannerLoadingScreen
import javax.inject.Inject

@HiltViewModel
class FaceScannerScreenViewModel @Inject constructor(private val navigator: AppNavigator):ViewModel() {
    fun openLoadingScreen(){
        viewModelScope.launch {
            navigator.navigateTo(FaceScannerLoadingScreen())
        }
    }
}