package io.crypto.superapp.ui.screen.pin_lock

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.crypto.feature.navigation.destination.Destination
import io.crypto.feature.pin_lock.PinLockScreen

object PinLockDestination : Destination {
    override val route: String = "pin-lock"
}

@Composable
fun PinLockScreen(navController: NavController) {
    PinLockScreen(
        onBackClick = navController::popBackStack,
        onSuccess = {}
    )
}