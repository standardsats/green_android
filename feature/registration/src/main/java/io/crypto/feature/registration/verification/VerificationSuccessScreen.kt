package io.crypto.feature.registration.verification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.crypto.core.resources.R.string
import io.crypto.core.ui.components.SuperGradientButton
import io.crypto.core.ui.theme.TextMedium_25_30Dark
import io.crypto.feature.registration.R

@Composable
fun VerificationSuccessScreen(
    onNextClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            modifier = Modifier.padding(top = 40.dp),
            painter = painterResource(id = R.drawable.image_confirmed),
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(top = 40.dp),
            text = stringResource(id = string.verification_success),
            style = TextMedium_25_30Dark
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SuperGradientButton(
                text = stringResource(id = string.next),
                onClick = onNextClick,
            )
        }
    }
}