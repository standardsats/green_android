package io.crypto.superapp.ui.screen.registration

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.crypto.feature.navigation.destination.Destination
import io.crypto.feature.registration.id_confirmation.IDConfirmationScreen

object IDConfirmationDestination : Destination {
    override val route = "id-confirmation"
}

@Composable
fun IDConfirmationScreen(navController: NavController) {
    IDConfirmationScreen(
        onBackClick = navController::popBackStack,
        onTermsClick = { /*TODO*/ },
        onNextButtonClick = {
            navController.navigate(
                CaptureIDHintDestination.route
            )
        }
    )
}