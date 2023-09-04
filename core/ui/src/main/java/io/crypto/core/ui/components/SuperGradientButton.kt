package io.crypto.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.crypto.core.ui.theme.FilledButtonGradient
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.TextRegular_W700_19_White

@Composable
fun SuperGradientButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .height(46.dp)
            .background(
                shape = RoundedCornerShape(5.dp),
                brush = FilledButtonGradient
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = TextRegular_W700_19_White
            )
        }
    }
}

@Preview
@Composable
fun FilledButtonPreview() {
    SuperAppTheme {
        Surface {
            SuperGradientButton(
                text = "Create",
                onClick = {}
            )
        }
    }
}