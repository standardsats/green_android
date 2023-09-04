package io.crypto.feature.registration.capture.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    fun onPhotoCaptured(bitmap: Bitmap) {
        updateCapturedPhotoState(bitmap)
    }

    fun onCapturedPhotoConsumed() {
        updateCapturedPhotoState(null)
    }

    private fun updateCapturedPhotoState(updatedPhoto: Bitmap?) {
        _state.value.capturedImage?.recycle()
        _state.update {
            it.copy(capturedImage = updatedPhoto)
        }
    }
}