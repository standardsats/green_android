package io.crypto.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.TextBold_19Blue

@Composable
fun SuperTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    style: TextStyle = TextBold_19Blue
) {
    TextButton(
        modifier = modifier
            .height(46.dp)
            .background(
                shape = RoundedCornerShape(5.dp),
                color = Color.White
            ),
        contentPadding = PaddingValues(),
        onClick = onClick,
        shape = RoundedCornerShape(5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = style
            )
        }
    }
}

@Preview
@Composable
fun SuperTextButtonPreview() {
    SuperAppTheme {
        Surface {
            SuperTextButton(
                text = "Create",
                onClick = {}
            )
        }
    }
}