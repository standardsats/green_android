package io.crypto.feature.navigation.destination

import android.os.Parcelable
import io.crypto.feature.navigation.Constants
import io.crypto.feature.navigation.helper.encodeForRoute
import io.crypto.feature.navigation.parcelables.ParcelableNavTypeSerializer

interface Destination {
    val route: String
}

interface DestinationWithNavArgs : Destination {
    val baseRoute: String

    override val route
        get() = "$baseRoute/{${Constants.NAV_ARGUMENTS}}"

    fun create(navArgs: Parcelable): String {
        val navArgsAsString = encodeForRoute(
            ParcelableNavTypeSerializer(navArgs::class.java)
                .toRouteString(
                    navArgs
                )
        )
        return "$baseRoute/$navArgsAsString"
    }
}