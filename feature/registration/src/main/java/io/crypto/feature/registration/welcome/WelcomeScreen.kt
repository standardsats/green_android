package io.crypto.feature.registration.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.crypto.core.resources.R
import io.crypto.core.ui.components.SuperGradientButton
import io.crypto.core.ui.components.SuperOutlinedButton
import io.crypto.core.ui.theme.LightGray
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.TextMedium20_50Grey
import io.crypto.core.ui.theme.TextMedium_37_50Dark

@Composable
fun WelcomeScreen(
    onLanguageClick: () -> Unit,
    onCreateWalletClick: () -> Unit,
    onAlreadyHaveClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // Changes according to state properties
        Language(
            text = stringResource(id = R.string.en),
            onClick = onLanguageClick
        )

        Image(
            modifier = Modifier
                .fillMaxWidth(0.45f)
                .aspectRatio(1f)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .padding(top = 56.dp)
                .background(Color.White)
                .fillMaxHeight()
        ) {
            Text(
                modifier = Modifier.padding(
                    top = 34.dp,
                    start = 34.dp,
                    end = 34.dp
                ),
                text = stringResource(id = R.string.welcome_to_wallet),
                style = TextMedium_37_50Dark
            )

            SuperGradientButton(
                modifier = Modifier.padding(
                    top = 62.dp,
                    start = 30.dp,
                    end = 30.dp
                ),
                text = stringResource(id = R.string.create_wallet),
                onClick = onCreateWalletClick
            )

            SuperOutlinedButton(
                modifier = Modifier.padding(
                    top = 23.dp,
                    start = 30.dp,
                    end = 30.dp
                ),
                text = stringResource(id = R.string.already_have_wallet),
                onClick = onAlreadyHaveClick
            )
        }
    }
}

@Composable
private fun Language(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(top = 24.dp, end = 24.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ) {
        Row(
            modifier = Modifier.clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_language),
                contentDescription = null,
                tint = LightGray
            )

            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = text,
                style = TextMedium20_50Grey
            )
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    SuperAppTheme {
        Surface {
            WelcomeScreen(
                onLanguageClick = {},
                onCreateWalletClick = {},
                onAlreadyHaveClick = {}
            )
        }
    }
}