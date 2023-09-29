package io.crypto.feature.settings.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import io.crypto.feature.registration.R

@Composable
fun CustomSwitchButton(value: Boolean, onValueChange: (Boolean) -> Unit) {
    IconButton(onClick = { onValueChange(!value) }) {
        Image(
            painter = painterResource(id = R.drawable.ic_switch_button),
            modifier = Modifier.rotate(if (value) 0f else 180f),
            contentDescription = null
        )
    }
}