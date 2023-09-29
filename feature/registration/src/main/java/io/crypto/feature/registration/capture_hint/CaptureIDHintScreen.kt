package io.crypto.feature.registration.capture_hint

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
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
import io.crypto.core.ui.theme.Dark
import io.crypto.core.ui.theme.TextMedium_25_30Dark
import io.crypto.feature.registration.R

@Composable
fun CaptureIDHintScreen(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
    ) {
        Icon(
            modifier = Modifier.clickable(onClick = onBackClick),
            painter = painterResource(id = io.crypto.core.resources.R.drawable.ic_arrow_back),
            contentDescription = null,
            tint = Dark
        )

        BodyContent(
            onNextClick = onNextClick
        )
    }
}

@Composable
fun BodyContent(onNextClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            painter = painterResource(
                id = R.drawable.image_hold_document
            ),
            contentDescription = null
        )

        Text(
            text = stringResource(id = string.hold_document),
            style = TextMedium_25_30Dark,
        )

        SuperGradientButton(
            text = stringResource(id = string.next),
            onClick = onNextClick
        )
    }
}