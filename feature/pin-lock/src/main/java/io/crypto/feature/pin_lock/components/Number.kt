package io.crypto.feature.pin_lock.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.crypto.core.ui.theme.Border
import io.crypto.core.ui.theme.TextMedium_32Dark

@Composable
fun NumberKey(
    value: Int,
    onPressed: (value: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .background(Color.White, RoundedCornerShape(4.dp))
            .border(BorderStroke(1.dp, Border), RoundedCornerShape(4.dp))
            .clickable {
                onPressed(value)
            }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value.toString(),
            textAlign = TextAlign.Center,
            style = TextMedium_32Dark
        )
    }
}