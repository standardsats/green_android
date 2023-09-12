package io.crypto.feature.registration.send_photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import io.crypto.core.resources.R
import io.crypto.core.ui.components.SuperGradientButton
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.TextRegular_25_30_Dark

@Composable
fun ExamplePhotoScreen(
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
            modifier = Modifier.padding(top = 27.dp)
        )

        Text(
            modifier = Modifier.padding(top = 26.dp),
            text = stringResource(id = R.string.confirm_picture),
            style = TextRegular_25_30_Dark,
        )

        Image(
            painter = painterResource(id = R.drawable.img_person),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 70.dp)
                .align(Alignment.CenterHorizontally),
        )


        Spacer(modifier = Modifier.weight(1f))

        SuperGradientButton(
            modifier = Modifier
                .padding(bottom = 130.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.take_photo),
            onClick = onNextButtonClick
        )
    }
}


@Preview
@Composable
fun ExamplePhotoScreenPreview() {
    SuperAppTheme {
        Surface {
            ExamplePhotoScreen(
                onNextButtonClick = {},
            )
        }
    }
}