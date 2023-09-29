package io.crypto.feature.pin_lock

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PinLockViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(
        // Need to fetch password from dataStore
        PinLockUIState(
            password = ""
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onNumberPressed(value: Int) {
        if (_uiState.value.password.length < 6) {
            _uiState.update {
                val newValue = it.password.plus(value)
                it.copy(
                    password = newValue
                )
            }

            /*TODO need to check compatibility*/
        }
    }

    fun onDelete() {
        val password = _uiState.value.password
        if (password.isNotEmpty()) {
            _uiState.update {
                it.copy(
                    password = password.take(password.length - 1)
                )
            }
        }
    }
}

data class PinLockUIState(
    val password: String
)