package io.crypto.feature.navigation.helper

import androidx.lifecycle.SavedStateHandle
import io.crypto.feature.navigation.Constants.NAV_ARGUMENTS

public inline fun <reified T> SavedStateHandle.navArgs(): T {
    return requireNotNull(this[NAV_ARGUMENTS])
}
