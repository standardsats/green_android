package io.crypto.feature.pin_lock.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.crypto.core.ui.theme.AzureishWhite
import io.crypto.core.ui.theme.DarkTurquoise

@Composable
fun PinDots(
    modifier: Modifier = Modifier,
    enteredDigitsCount: Int
) {
    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(6) {
            Dot(enabled = it < enteredDigitsCount)
        }
    }
}

@Composable
fun RowScope.Dot(
    enabled: Boolean = false
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(
                color = if (enabled) DarkTurquoise else AzureishWhite,
                shape = CircleShape
            )
    )
}

