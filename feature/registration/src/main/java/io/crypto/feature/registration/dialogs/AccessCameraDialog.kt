package io.crypto.feature.registration.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.crypto.core.resources.R
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.TextBold_19Dark
import io.crypto.core.ui.theme.TextRegular_15_20Blue
import io.crypto.core.ui.theme.TextRegular_15_20Pink
import io.crypto.core.ui.theme.White


@Composable
fun AccessCameraDialog(
    setShowDialog: (Boolean) -> Unit,
    onAllowBtnClick: () -> Unit,
    onRejectBtnClick: () -> Unit,
) {

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(48.dp)) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = stringResource(id = R.string.allow_camera_access),
                        style = TextBold_19Dark,
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .align(Alignment.CenterHorizontally),
                    )

                    Text(
                        text = stringResource(id = R.string.allow),
                        style = TextRegular_15_20Blue,
                        modifier = Modifier
                            .padding(top = 22.dp)
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                onAllowBtnClick()
                            },
                    )

                    Text(
                        text = stringResource(id = R.string.reject),
                        style = TextRegular_15_20Pink,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                onRejectBtnClick()
                            }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AccessCameraDialogPreview() {
    SuperAppTheme {
        Surface {
            AccessCameraDialog(
                setShowDialog = {},
                onAllowBtnClick = {},
                onRejectBtnClick = {}
            )
        }
    }
}