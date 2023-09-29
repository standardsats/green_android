package io.crypto.feature.registration.send_photo._components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.crypto.core.ui.theme.TextRegular_15_20Dark

@Composable
fun CorrectionFaceView(
    modifier: Modifier,
    iconId: Int,
    text: Int
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = iconId),
            contentDescription = null
        )

        Text(
            text = stringResource(id = text),
            modifier = modifier.padding(8.dp),
            style = TextRegular_15_20Dark
        )
    }
}
