package io.crypto.feature.registration.verification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.crypto.core.resources.R.string
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.TextMedium_25_30Dark
import io.crypto.core.ui.theme.TextRegular_14_17_5Grey
import io.crypto.core.ui.theme.TextRegular_15_20Dark
import io.crypto.core.resources.R

@Composable
fun VerificationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            modifier = Modifier.padding(top = 15.dp, start = 10.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_verification_waiting),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally),
        )

        Text(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = string.identity_authenticating),
            style = TextMedium_25_30Dark,
            textAlign = TextAlign.Center,
        )

        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = string.redirected_shortly),
            style = TextRegular_15_20Dark,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator()

            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = string.sending_data),
                style = TextRegular_14_17_5Grey
            )
        }
    }
}

@Preview
@Composable
fun VerificationScreenPreview() {
    SuperAppTheme {
        Surface {
            VerificationScreen()
        }
    }
}