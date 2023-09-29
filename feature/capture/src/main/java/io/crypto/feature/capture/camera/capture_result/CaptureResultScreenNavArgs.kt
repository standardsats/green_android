package io.crypto.feature.capture.camera.capture_result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CaptureResultScreenNavArgs(
    val filePath: String
) : Parcelable
