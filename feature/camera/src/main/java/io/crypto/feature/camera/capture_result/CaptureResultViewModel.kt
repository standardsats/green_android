package io.crypto.feature.camera.capture_result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.crypto.feature.navigation.helper.navArgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CaptureResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val navArgs: CaptureResultScreenNavArgs = savedStateHandle.navArgs()

    private val _uiState = MutableStateFlow(CaptureResultUIState(navArgs.filePath))
    val uiState = _uiState.asStateFlow()
}

data class CaptureResultUIState(
    val filePath: String
)