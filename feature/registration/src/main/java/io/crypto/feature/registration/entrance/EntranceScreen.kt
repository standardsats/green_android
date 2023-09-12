package io.crypto.feature.registration.entrance

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.crypto.core.ui.theme.LightGray
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.White
import io.crypto.core.resources.R
import io.crypto.core.ui.components.SuperGradientButton
import io.crypto.core.ui.components.SuperOutlinedButton
import io.crypto.core.ui.theme.Light
import io.crypto.core.ui.theme.TextBold_36Dark
import io.crypto.core.ui.theme.TextMedium20_50Grey

@Composable
fun EntranceScreen(
    onClick: () -> Unit,
    onCreateWalletClick: () -> Unit,
    onHaveWalletClick: () -> Unit
) {


    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(Light),
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
                        text = stringResource(id = R.string.en),
                        style = TextMedium20_50Grey
                    )
                }
            }

            Text(
                modifier = Modifier.padding(top = 50.dp, start = 20.dp, end = 20.dp),
                text = stringResource(id = R.string.welcome_to_wallet),
                style = TextBold_36Dark
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(White),
            verticalArrangement = Arrangement.Bottom
        ) {

            SuperGradientButton(
                modifier = Modifier
                    .padding(bottom = 12.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.create_wallet),
                onClick = onCreateWalletClick
            )

            SuperOutlinedButton(
                modifier = Modifier.padding(bottom = 96.dp, start = 20.dp, end = 20.dp),
                text = stringResource(id = R.string.already_have_wallet),
                onClick = onHaveWalletClick
            )
        }
    }
}


@Preview
@Composable
fun EntranceScreenPreview() {
    SuperAppTheme {
        Surface {
            EntranceScreen(
                onClick = {},
                onCreateWalletClick = {},
                onHaveWalletClick = {}
            )
        }
    }
}