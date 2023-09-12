package io.crypto.feature.camera.capture

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import io.crypto.feature.camera.capture.camera.CameraScreen
import io.crypto.feature.camera.capture.no_permission.NoPermissionsScreen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CaptureScreen(
    onCapture: (filePath: String) -> Unit,
    onBackClick: () -> Unit
) {

    val cameraPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)

    CaptureScreen(
        onBackClick = onBackClick,
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest,
        onCapture = onCapture
    )
}

@Composable
private fun CaptureScreen(
    hasPermission: Boolean,
    onBackClick: () -> Unit,
    onRequestPermission: () -> Unit,
    onCapture: (filePath: String) -> Unit,
) {

    if (hasPermission) {
        CameraScreen(
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

@Preview
@Composable
private fun Preview_CaptureScreen() {
    CaptureScreen(
        hasPermission = true,
        onBackClick = {},
        onRequestPermission = {},
        onCapture = {}
    )
}