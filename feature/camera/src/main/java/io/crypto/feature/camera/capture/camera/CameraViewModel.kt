package io.crypto.feature.camera.capture.camera

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.crypto.feature.camera.utils.createFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(CameraState())
    val uiState = _uiState.asStateFlow()

    fun onPhotoCaptured(context: Context, bitmap: Bitmap) {
        _uiState.update { it.copy(loading = true) }
        createFileForBitmap(context, bitmap)
    }

    fun onPhotoConsumed() {
        _uiState.update {
            it.copy(
                loading = false,
                capturedImageFilePath = null
            )
        }
    }

    private fun createFileForBitmap(context: Context, bitmap: Bitmap) =
        viewModelScope.launch(Dispatchers.IO) {
            val filePath = bitmap.createFile(context)
            _uiState.update {
                it.copy(
                    capturedImageFilePath = filePath.absolutePath
                )
            }
        }
}

data class CameraState(
    val capturedImageFilePath: String? = null,
    val loading: Boolean = false,
)