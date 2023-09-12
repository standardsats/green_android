package io.crypto.superapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import io.crypto.feature.camera.capture_result.CaptureResultScreenNavArgs
import io.crypto.feature.navigation.Constants.NAV_ARGUMENTS
import io.crypto.feature.navigation.parcelables.ParcelableNavType
import io.crypto.superapp.ui.screen.camera.CaptureDestination
import io.crypto.superapp.ui.screen.camera.CaptureResultDestination
import io.crypto.superapp.ui.screen.camera.CaptureResultScreen
import io.crypto.superapp.ui.screen.camera.CaptureScreen
import io.crypto.superapp.ui.screen.registration.CaptureIDHintDestination
import io.crypto.superapp.ui.screen.registration.CaptureIDHintScreen
import io.crypto.superapp.ui.screen.registration.IDConfirmationDestination
import io.crypto.superapp.ui.screen.registration.IDConfirmationScreen
import io.crypto.superapp.ui.screen.registration.WelcomeDestination
import io.crypto.superapp.ui.screen.registration.WelcomeScreen

@Composable
fun SuperAppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = RegistrationGraph.route
    ) {
        navigation(
            route = RegistrationGraph.route,
            startDestination = WelcomeDestination.route
        ) {
            composable(WelcomeDestination.route) {
                WelcomeScreen(navController)
            }

            composable(IDConfirmationDestination.route) {
                IDConfirmationScreen(navController)
            }

            composable(CaptureIDHintDestination.route) {
                CaptureIDHintScreen(navController)
            }

            composable(CaptureDestination.route) {
                CaptureScreen(navController)
            }

            composable(
                route = CaptureResultDestination.route,
                arguments = listOf(
                    navArgument(name = NAV_ARGUMENTS) {
                        type = ParcelableNavType(CaptureResultScreenNavArgs::class.java)
                    }
                )
            ) {
                CaptureResultScreen(navController)
            }
        }
    }
}