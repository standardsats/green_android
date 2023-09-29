package io.crypto.feature.capture.video.capture

import android.Manifest
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import io.crypto.feature.capture.camera.capture.no_permission.NoPermissionsScreen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun VideoCaptureScreen(
    onCapture: (filePath: String) -> Unit,
    onBackClick: () -> Unit
) {
    val cameraPermissionState: PermissionState =
        rememberPermissionState(Manifest.permission.CAMERA)

    VideoCaptureScreen(
        hasPermission = cameraPermissionState.status.isGranted,
        onBackClick = onBackClick,
        onRequestPermission = cameraPermissionState::launchPermissionRequest,
        onCapture = onCapture
    )
}

@Composable
private fun VideoCaptureScreen(
    hasPermission: Boolean,
    onBackClick: () -> Unit,
    onRequestPermission: () -> Unit,
    onCapture: (filePath: String) -> Unit,
) {
    if (hasPermission) {
        VideoScreen(
            onBackClick = onBackClick,
            onCapture = onCapture
        )
    } else {
        NoPermissionsScreen(
            onRequestPermission = onRequestPermission,
            onReject = onBackClick
        )
    }
}