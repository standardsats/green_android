package io.crypto.feature.registration.capture

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import io.crypto.feature.registration.capture.camera.CameraScreen
import io.crypto.feature.registration.capture.no_permission.NoPermissionsScreen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen() {

    val cameraPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)

    CaptureScreen(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest
    )
}

@Composable
private fun CaptureScreen(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit
) {

    if (hasPermission) {
        CameraScreen()
    } else {
        NoPermissionsScreen(onRequestPermission)
    }
}

@Preview
@Composable
private fun Preview_CaptureScreen() {
    CaptureScreen(
        hasPermission = true,
        onRequestPermission = {}
    )
}