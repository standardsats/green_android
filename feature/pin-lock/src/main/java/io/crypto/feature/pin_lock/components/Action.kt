package io.crypto.feature.pin_lock.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import io.crypto.core.resources.R

@Composable
fun ActionKey(
    onPressed: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(24.dp)
            .clickable(onClick = onPressed),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.ic_delete_password
            ),
            contentDescription = null
        )
    }
}