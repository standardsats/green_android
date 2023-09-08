package io.crypto.feature.registration.id_confirmation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.crypto.core.resources.R.string
import io.crypto.core.ui.components.SuperGradientButton
import io.crypto.core.ui.theme.TextBold_19Dark
import io.crypto.core.ui.theme.TextMedium40_50Dark
import io.crypto.core.ui.theme.TextMedium_14_15Dark
import io.crypto.core.ui.theme.TextRegular_10_15Dark
import io.crypto.core.ui.theme.TextRegular_14Dark
import io.crypto.feature.registration.R

@Composable
fun IdConfirmationScreen(
    navController: NavController,
    onBackClick: () -> Unit,
    onTermsClick: () -> Unit,
    onNextButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 40.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
            )
    ) {
        Text(
            text = stringResource(id = string.id_confirmation),
            style = TextMedium40_50Dark,
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = string.less_than_3_minutes),
            style = TextRegular_14Dark,
        )

        InfoSection(
            modifier = Modifier.padding(top = 52.dp),
            title = stringResource(id = string.identification),
            subtitle = stringResource(id = string.take_photo_id),
            iconRes = R.drawable.ic_id_card
        )

        InfoSection(
            modifier = Modifier.padding(top = 30.dp),
            title = stringResource(id = string.short_video),
            subtitle = stringResource(id = string.take_video),
            iconRes = R.drawable.ic_short_video
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp)
                .clickable(onClick = onTermsClick)
                .align(CenterHorizontally),
            text = stringResource(id = string.next_consent),
            style = TextRegular_10_15Dark,
            textAlign = TextAlign.Center
        )

        SuperGradientButton(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = stringResource(id = string.next),
            onClick = onNextButtonClick
        )
    }
}

@Composable
private fun InfoSection(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    title: String,
    subtitle: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null
        )

        Column(
            modifier = Modifier.padding(
                start = 16.dp
            ),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = title,
                style = TextBold_19Dark,
            )

            Text(
                text = subtitle,
                style = TextMedium_14_15Dark,
            )
        }
    }
}