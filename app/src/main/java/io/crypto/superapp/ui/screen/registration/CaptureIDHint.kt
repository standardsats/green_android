package io.crypto.superapp.ui.screen.registration

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.crypto.feature.navigation.destination.Destination
import io.crypto.feature.registration.capture_hint.CaptureIDHintScreen
import io.crypto.superapp.ui.screen.camera.CaptureDestination

object CaptureIDHintDestination : Destination {
    override val route = "capture-id-hint"
}

@Composable
fun CaptureIDHintScreen(navController: NavController) {
    CaptureIDHintScreen(
        onBackClick = navController::popBackStack,
        onNextClick = {
            navController.navigate(CaptureDestination.route)
        }
    )
}