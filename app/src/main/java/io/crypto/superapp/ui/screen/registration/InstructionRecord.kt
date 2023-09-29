package io.crypto.superapp.ui.screen.registration

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.crypto.feature.navigation.destination.Destination
import io.crypto.feature.registration.send_photo.InstructionRecordScreen
import io.crypto.superapp.ui.screen.video.VideoCaptureDestination

object InstructionRecordDestination : Destination {
    override val route = "instruction-record"
}

@Composable
fun InstructionRecordScreen(navController: NavController) {
    InstructionRecordScreen(
        onNextButtonClick = {
            navController.navigate(
                VideoCaptureDestination.route
            )
        },
        onBackClick = navController::popBackStack
    )
}