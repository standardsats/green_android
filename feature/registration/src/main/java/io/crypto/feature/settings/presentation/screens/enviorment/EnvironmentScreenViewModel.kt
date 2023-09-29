package io.crypto.feature.settings.presentation.screens.enviorment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import io.crypto.feature.settings.navigation.AppNavigator
import io.crypto.feature.settings.presentation.screens.store.StoreScreen
import javax.inject.Inject

@HiltViewModel
class EnvironmentScreenViewModel @Inject constructor(private val navigator: AppNavigator):ViewModel(){
    fun openStoreScreen(){
        viewModelScope.launch {
            navigator.navigateTo(StoreScreen( ))
        }
    }
    fun  backToSettings(){
        viewModelScope.launch {
            navigator.back()
        }
    }
}