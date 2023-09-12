package io.crypto.superapp.ui.navigation

import io.crypto.feature.navigation.destination.Destination

object RegistrationGraph : Destination {
    override val route = "registration"
}

object SendGraph : Destination {
    override val route = "send"
}

object SettingsGraph : Destination {
    override val route = "settings"
}