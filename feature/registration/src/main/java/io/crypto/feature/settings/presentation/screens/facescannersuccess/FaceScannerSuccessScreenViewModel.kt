package io.crypto.feature.settings.presentation.screens.facescannersuccess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import io.crypto.feature.settings.navigation.AppNavigator
import javax.inject.Inject

@HiltViewModel
class FaceScannerSuccessScreenViewModel @Inject constructor(private val navigator: AppNavigator):ViewModel() {

    fun backToHome(){
        viewModelScope.launch {
            navigator.backUntilRoot()
        }
    }
}