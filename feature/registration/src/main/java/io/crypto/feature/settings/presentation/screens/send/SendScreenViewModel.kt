package io.crypto.feature.settings.presentation.screens.send

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import io.crypto.feature.settings.navigation.AppNavigator
import io.crypto.feature.settings.presentation.screens.camera_info.CameraInfoScreen
import javax.inject.Inject

@HiltViewModel
class SendScreenViewModel @Inject constructor(private val navigator: AppNavigator):ViewModel() {
    fun openInfoScreen(){
        viewModelScope.launch {
            navigator.navigateTo(CameraInfoScreen())
        }
    }
    fun back(){
        viewModelScope.launch {
            navigator.back()
        }
    }
}