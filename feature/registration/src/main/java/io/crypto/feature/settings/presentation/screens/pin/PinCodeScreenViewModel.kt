package io.crypto.feature.settings.presentation.screens.pin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import io.crypto.feature.settings.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class PinCodeScreenViewModel @Inject constructor(private val navigator: AppNavigator):ViewModel() {
    fun backToSettings(){
        viewModelScope.launch{
            navigator.back()
        }
    }
}