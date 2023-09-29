package io.crypto.feature.settings.presentation.screens.facescannererror

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import io.crypto.feature.settings.navigation.AppNavigator
import javax.inject.Inject

class FaceScannerErrorScreenViewModel @Inject constructor(private val navigator: AppNavigator):ViewModel() {
    fun backToCameraScreen(){
        viewModelScope.launch {
            navigator.back()
        }
    }
}