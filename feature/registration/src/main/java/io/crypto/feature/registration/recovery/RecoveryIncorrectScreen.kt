package io.crypto.feature.registration.recovery

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.sp
import io.crypto.core.resources.R
import io.crypto.core.ui.components.SuperGradientButton
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.TextRegular_14Dark
import io.crypto.core.ui.theme.TextRegular_35_50_Dark

@Composable
fun RecoveryIncorrectScreen(
    onNextButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            modifier = Modifier.padding(top = 20.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_incorrect),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 80.dp)
                .size(95.dp)
                .align(Alignment.CenterHorizontally),
        )

        Text(
            text = stringResource(id = R.string.title_incorrect),
            style = TextRegular_35_50_Dark,
            modifier = Modifier.padding(top = 62.dp, start = 26.dp, end = 26.dp)
        )

        Text(
            modifier = Modifier.padding(top = 30.dp, start = 26.dp, end = 26.dp),
            text = stringResource(id = R.string.desc_incorrect),
            style = TextRegular_14Dark,
            letterSpacing = (-0.3).sp
        )

        Spacer(modifier = Modifier.weight(1f))

        SuperGradientButton(
            modifier = Modifier
                .padding(bottom = 130.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.next),
            onClick = onNextButtonClick
        )
    }
}

@Preview
@Composable
fun RecoveryIncorrectScreenPreview() {
    SuperAppTheme {
        Surface {
            RecoveryIncorrectScreen(
                onNextButtonClick = {},
            )
        }
    }
}