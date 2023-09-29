package io.crypto.superapp.ui.screen.video

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.crypto.feature.capture.video.capture.VideoCaptureScreen
import io.crypto.feature.navigation.destination.Destination

object VideoCaptureDestination : Destination {
    override val route = "video-capture"
}

@Composable
fun VideoCaptureScreen(navController: NavController) {
    VideoCaptureScreen(
        onBackClick = navController::popBackStack,
        onCapture = {
            navController.navigate(
                VideoPreviewDestination.create(it)
            )
        }
    )
}