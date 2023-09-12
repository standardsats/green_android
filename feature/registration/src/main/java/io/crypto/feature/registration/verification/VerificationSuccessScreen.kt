package io.crypto.feature.registration.verification

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import io.crypto.core.resources.R
import io.crypto.core.resources.R.string
import io.crypto.core.ui.components.SuperGradientButton
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.TextMedium_25_30Dark

@Composable
fun VerificationSuccessScreen(
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_confirmed),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 56.dp)
                .align(Alignment.CenterHorizontally),
        )

        Text(
            modifier = Modifier
                .padding(top = 100.dp, start = 8.dp, end = 6.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = string.verification_success),
            style = TextMedium_25_30Dark,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.weight(1f))

        SuperGradientButton(
            modifier = Modifier
                .padding(bottom = 130.dp)
                .fillMaxWidth(),
            text = stringResource(id = string.next),
            onClick = onNextClick,
        )
    }
}

@Preview
@Composable
fun VerificationSuccessScreenPreview() {
    SuperAppTheme {
        Surface {
            VerificationSuccessScreen(
                onNextClick = {}
            )
        }
    }
}