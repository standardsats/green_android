package io.crypto.superapp.ui.screen.camera

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.crypto.feature.capture.camera.capture.CaptureScreen
import io.crypto.feature.navigation.destination.Destination

object CaptureDestination : Destination {
    override val route = "capture"
}

@Composable
fun CaptureScreen(
    navController: NavController
) {
    CaptureScreen(
        onCapture = {
            navController.navigate(
                CaptureResultDestination.create(it)
            )
        },
        onBackClick = navController::popBackStack
    )
}