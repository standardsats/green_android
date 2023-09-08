package io.crypto.feature.registration.verification.fail

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.crypto.core.resources.R.string
import io.crypto.core.ui.components.SuperGradientButton
import io.crypto.core.ui.theme.TextMedium_25_30Dark
import io.crypto.core.ui.theme.TextRegular_20_30Dark
import io.crypto.feature.registration.R

@Composable
fun VerificationNotFoundScreen(
    onGoToMainClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.padding(top = 40.dp),
            painter = painterResource(id = R.drawable.image_verification_not_found),
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(top = 40.dp),
            text = stringResource(id = string.id_not_found),
            style = TextMedium_25_30Dark,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(id = string.verify_identity_post_office),
            style = TextRegular_20_30Dark,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SuperGradientButton(
                text = stringResource(id = string.go_back_main_page),
                onClick = onGoToMainClick,
            )
        }
    }
}