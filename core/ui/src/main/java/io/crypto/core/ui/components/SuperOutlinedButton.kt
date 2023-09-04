package io.crypto.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.crypto.core.ui.theme.LightGray
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.TextRegular_W700_19_Dark

@Composable
fun SuperOutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp),
        onClick = onClick,
        border = BorderStroke(
            width = 2.dp,
            color = LightGray
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Text(
            text = text,
            style = TextRegular_W700_19_Dark
        )
    }
}

@Preview
@Composable
fun SuperOutlinedButtonPreview() {
    SuperAppTheme {
        Surface {
            SuperOutlinedButton(
                text = "Already have wallet",
                onClick = {}
            )
        }
    }
}