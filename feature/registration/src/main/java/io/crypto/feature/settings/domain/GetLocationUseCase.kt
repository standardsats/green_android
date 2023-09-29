package io.crypto.feature.settings.domain

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import io.crypto.feature.settings.data.ILocationService
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(
    private val locationService: ILocationService
) {
    operator fun invoke(): Flow<LatLng?> = locationService.requestLocationUpdates()

}