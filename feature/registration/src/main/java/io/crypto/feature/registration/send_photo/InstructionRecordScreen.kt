package io.crypto.feature.registration.send_photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import io.crypto.core.ui.theme.Dark
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.TextRegular_25_30_Dark
import io.crypto.feature.registration.send_photo._components.CorrectionFaceView

@Composable
fun InstructionRecordScreen(
    onNextButtonClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical
            ),
    ) {

        Icon(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .clickable(onClick = onBackClick),
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            tint = Dark
        )

        Text(
            text = stringResource(id = R.string.make_short_video),
            style = TextRegular_25_30_Dark,
        )

        Box(modifier = Modifier.fillMaxWidth()) {

            Image(
                painter = painterResource(id = R.drawable.ic_face_instructor_vector),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 40.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CorrectionFaceView(
                        modifier = Modifier
                            .padding(top = 18.dp),
                        R.drawable.ic_full_face,
                        R.string.full_face
                    )
                    CorrectionFaceView(
                        modifier = Modifier
                            .padding(top = 20.dp),
                        R.drawable.ic_face_turn_right,
                        R.string.turn_right
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CorrectionFaceView(
                        modifier = Modifier
                            .padding(top = 12.dp),
                        R.drawable.ic_turn_left,
                        R.string.turn_left
                    )

                    CorrectionFaceView(
                        modifier = Modifier
                            .padding(top = 18.dp),
                        R.drawable.ic_face_turn_up,
                        R.string.turn_up
                    )
                }

                CorrectionFaceView(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    R.drawable.ic_face_down,
                    R.string.turn_down
                )
            }

        }

        Spacer(modifier = Modifier.height(8.dp))

        SuperGradientButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.ready_to_record_face),
            onClick = onNextButtonClick
        )
    }
}


@Preview
@Composable
fun InstructionRecordScreenPreview() {
    SuperAppTheme {
        Surface {
            InstructionRecordScreen(
                onNextButtonClick = {},
                onBackClick = {}
            )
        }
    }
}