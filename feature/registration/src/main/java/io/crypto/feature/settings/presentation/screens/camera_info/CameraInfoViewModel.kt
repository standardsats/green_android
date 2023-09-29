package io.crypto.feature.settings.presentation.screens.camera_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import io.crypto.feature.settings.navigation.AppNavigator
import io.crypto.feature.settings.presentation.screens.facescanner.FaceScanScreen
import javax.inject.Inject

@HiltViewModel
class CameraInfoViewModel @Inject constructor(private val navigator: AppNavigator):ViewModel() {
    fun openFaceScannerScreen(){
        viewModelScope.launch {
            navigator.replace(FaceScanScreen())
        }
    }
    fun back(){
        viewModelScope.launch {
            navigator.back()
        }
    }
}