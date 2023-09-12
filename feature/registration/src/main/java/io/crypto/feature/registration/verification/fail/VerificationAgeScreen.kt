package io.crypto.feature.registration.verification.fail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import io.crypto.core.ui.components.SuperTextButton
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.TextMedium_25_30Dark

@Composable
fun VerificationAgeScreen(
    onGoBackToMainClick: () -> Unit,
    onMistakeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_age_16),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 87.dp)
                .align(Alignment.CenterHorizontally),
        )

        Text(
            modifier = Modifier
                .padding(top = 95.dp, start = 9.dp, end = 6.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(id = string.underage_account),
            style = TextMedium_25_30Dark,
            textAlign = TextAlign.Center
        )
        SuperGradientButton(
            modifier = Modifier.padding(top = 45.dp),
            text = stringResource(id = string.go_back_main_page),
            onClick = onGoBackToMainClick,
        )

        SuperTextButton(
            modifier = Modifier.padding(top = 15.dp),
            text = stringResource(id = string.mistake),
            onClick = onMistakeClick
        )
    }
}

@Preview
@Composable
fun VerificationAgeScreenPreview() {
    SuperAppTheme {
        Surface {
            VerificationAgeScreen(
                onGoBackToMainClick = {},
                onMistakeClick = {}
            )
        }
    }
}