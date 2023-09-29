package io.crypto.feature.capture.video.capture_result

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoPreviewScreenNavArgs(
    val videoUri: String
) : Parcelable
