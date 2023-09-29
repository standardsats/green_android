package io.crypto.feature.capture.video.capture_result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.crypto.feature.navigation.helper.navArgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VideoPreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val navArgs: VideoPreviewScreenNavArgs = savedStateHandle.navArgs()

    private val _uiState = MutableStateFlow(VideoPreviewState(navArgs.videoUri))
    val uiState = _uiState.asStateFlow()
}

data class VideoPreviewState(val videoUri: String)