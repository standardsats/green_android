package io.crypto.superapp.ui.screen.registration

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.crypto.feature.navigation.destination.Destination
import io.crypto.feature.registration.welcome.WelcomeScreen
import io.crypto.superapp.ui.screen.pin_lock.PinLockDestination

object WelcomeDestination : Destination {
    override val route = "welcome"
}

@Composable
fun WelcomeScreen(navController: NavController) {
    WelcomeScreen(
        onLanguageClick = { /*TODO*/ },
        onCreateWalletClick = {
            navController.navigate(
                IDConfirmationDestination.route
            )
        },
        onAlreadyHaveClick = {
            navController.navigate(
                PinLockDestination.route
            )
        }
    )
}