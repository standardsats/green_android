package io.crypto.feature.registration.capture_result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.crypto.core.resources.R.string
import io.crypto.core.ui.components.SuperGradientButton
import io.crypto.core.ui.components.SuperOutlinedButton
import io.crypto.core.ui.theme.TextMedium_25_30Dark
import io.crypto.core.ui.theme.TextRegular_15_20Dark

@Composable
fun CaptureResultScreen(
    navController: NavController,
    onSendVerificationClick: () -> Unit,
    onTakeAnotherPhotoClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1 / 2f),
//            bitmap = { /* TODO */ },
            imageVector = Icons.Default.ThumbDown,
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = string.id_card),
            style = TextMedium_25_30Dark,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = stringResource(id = string.make_sure_data_visible),
            style = TextRegular_15_20Dark,
            textAlign = TextAlign.Center
        )

        SuperGradientButton(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = string.send_verification),
            onClick = onSendVerificationClick
        )

        SuperOutlinedButton(
            modifier = Modifier.padding(top = 12.dp),
            text = stringResource(id = string.take_another_photo),
            onClick = onTakeAnotherPhotoClick
        )
    }
}