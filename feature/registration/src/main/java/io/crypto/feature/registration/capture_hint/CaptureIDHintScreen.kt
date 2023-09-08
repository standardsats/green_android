package io.crypto.feature.registration.capture_hint

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import io.crypto.core.resources.R.string
import io.crypto.core.ui.components.SuperGradientButton
import io.crypto.core.ui.theme.TextMedium_25_30Dark
import io.crypto.feature.registration.R

@Composable
fun CaptureIDHintScreen(
    navController: NavController,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = string.hold_document),
            style = TextMedium_25_30Dark,
        )

        Image(
            painter = painterResource(
                id = R.drawable.image_hold_document
            ),
            contentDescription = null
        )

        SuperGradientButton(
            text = stringResource(id = string.next),
            onClick = onNextClick
        )
    }
}