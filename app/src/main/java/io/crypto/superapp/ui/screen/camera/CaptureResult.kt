package io.crypto.superapp.ui.screen.camera

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.crypto.feature.capture.camera.capture_result.CaptureResultScreen
import io.crypto.feature.capture.camera.capture_result.CaptureResultScreenNavArgs
import io.crypto.feature.navigation.destination.DestinationWithNavArgs
import io.crypto.superapp.ui.screen.registration.InstructionRecordDestination

object CaptureResultDestination : DestinationWithNavArgs {
    override val baseRoute: String = "capture-result"

    fun create(filePath: String) = create(
        CaptureResultScreenNavArgs(filePath)
    )
}

@Composable
fun CaptureResultScreen(navController: NavController) {
    CaptureResultScreen(
        onSendVerificationClick = {
            navController.navigate(InstructionRecordDestination.route)
        },
        onTakeAnotherPhotoClick = navController::popBackStack
    )
}