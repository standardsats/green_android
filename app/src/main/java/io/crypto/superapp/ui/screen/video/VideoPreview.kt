package io.crypto.superapp.ui.screen.video

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.crypto.feature.capture.video.capture_result.VideoPreviewScreen
import io.crypto.feature.capture.video.capture_result.VideoPreviewScreenNavArgs
import io.crypto.feature.navigation.destination.DestinationWithNavArgs

object VideoPreviewDestination : DestinationWithNavArgs {
    override val baseRoute: String = "video-preview"

    fun create(videoUri: String) = create(
        VideoPreviewScreenNavArgs(videoUri)
    )
}

@Composable
fun VideoPreviewScreen(navController: NavController) {
    VideoPreviewScreen(
        onSendVerificationClick = {},
        onTakeAnotherVideoClick = navController::popBackStack
    )
}