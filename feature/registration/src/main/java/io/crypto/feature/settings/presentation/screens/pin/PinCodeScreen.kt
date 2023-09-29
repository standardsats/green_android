package io.crypto.feature.settings.presentation.screens.pin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import io.crypto.feature.registration.R
import io.crypto.feature.settings.navigation.AppScreen
import io.crypto.feature.settings.presentation.components.CustomKeyboard
import io.crypto.feature.settings.utils.mainColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class PasswordState {
    OLD_PASSWORD, NEW_PASSWORD, NEW_PASSWORD_RESET
}

class PinCodeScreen : AppScreen() {
    @Composable
    override fun Content() {
        PinCodeScreenContent()
    }

    @Composable
    private fun PinCodeScreenContent() {
        val viewModel = getViewModel<PinCodeScreenViewModel>()
        val oldPassword = "123456"
        var pinLength by remember {
            mutableIntStateOf(0)
        }
        var errorState by remember {
            mutableStateOf(false)
        }

        var inputOldPassword by remember {
            mutableStateOf("")
        }
        var inputNewPassword by remember {
            mutableStateOf("")
        }
        var inputNewResetPassword by remember {
            mutableStateOf("")
        }
        var passwordState: PasswordState by remember {
            mutableStateOf(PasswordState.OLD_PASSWORD)
        }
        val scope = rememberCoroutineScope()


        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                IconButton(
                    onClick = { viewModel.backToSettings() },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(0.dp)
                        .weight(0.5f)
                )
                Text(
                    modifier = Modifier.padding(start = 32.dp, end = 46.dp),
                    text = if (passwordState == PasswordState.OLD_PASSWORD) "Enter your old PIN" else if (passwordState == PasswordState.NEW_PASSWORD) "Enter a new PIN code" else "Re-enter the new PIN-Code",
                    style = TextStyle(
                        fontSize = 40.sp,
                        lineHeight = 50.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF09282D),
                    )
                )
                Text(
                    text = if (passwordState == PasswordState.OLD_PASSWORD) "This is to make sure it is you who is changing the PIN." else if (passwordState == PasswordState.NEW_PASSWORD) "Enter a new PIN, make sure you remember it" else "This is necessary to confirm the PIN-Code change.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF09282D),
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 16.dp)
                )

                Spacer(
                    modifier = Modifier
                        .height(0.dp)
                        .weight(0.3f)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (errorState) Color.Red else if (pinLength >= 1) mainColor else Color(
                                    0xFFDFE5F4
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (errorState) Color.Red else if (pinLength >= 2) mainColor else Color(
                                    0xFFDFE5F4
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (errorState) Color.Red else if (pinLength >= 3) mainColor else Color(
                                    0xFFDFE5F4
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (errorState) Color.Red else if (pinLength >= 4) mainColor else Color(
                                    0xFFDFE5F4
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (errorState) Color.Red else if (pinLength >= 5) mainColor else Color(
                                    0xFFDFE5F4
                                )
                            )
                    )
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(50))
                            .background(
                                if (errorState) Color.Red else if (pinLength >= 6) mainColor else Color(
                                    0xFFDFE5F4
                                )
                            )
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.dp)
                        .weight(2f)
                        .clip(
                            RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                        )
                )
                CustomKeyboard({
                    when (passwordState) {
                        PasswordState.OLD_PASSWORD -> {
                            if (inputOldPassword.length < 6) {
                                pinLength++
                                inputOldPassword += it
                                if (inputOldPassword.length == 6 && inputOldPassword == oldPassword) {
                                    scope.launch {
                                        passwordState = PasswordState.NEW_PASSWORD
                                        pinLength = 0
                                    }
                                } else if (inputOldPassword.length == 6) {
                                    scope.launch {
                                        errorState = true
                                        delay(300)
                                        errorState = false
                                        inputOldPassword = ""
                                        pinLength = 0
                                    }
                                }
                            }
                        }

                        PasswordState.NEW_PASSWORD -> {
                            if (inputNewPassword.length < 6) {
                                pinLength++
                                inputNewPassword += it
                                if (inputNewPassword.length == 6) {
                                    scope.launch {
                                        delay(300)
                                        passwordState = PasswordState.NEW_PASSWORD_RESET
                                        pinLength = 0
                                    }
                                }
                            }
                        }

                        PasswordState.NEW_PASSWORD_RESET -> {
                            if (inputNewResetPassword.length < 6) {
                                pinLength++
                                inputNewResetPassword += it
                                if (inputNewResetPassword.length == 6 && inputNewResetPassword == inputNewPassword) {
                                    //todo
                                } else if (inputNewResetPassword.length == 6) {
                                    scope.launch {
                                        errorState = true
                                        delay(300)
                                        errorState = false
                                        pinLength = 0
                                        inputNewResetPassword = ""
                                    }
                                }
                            }
                        }
                    }

                }) {
                    if (pinLength > 0) {
                        when (passwordState) {
                            PasswordState.OLD_PASSWORD -> {
                                if (inputOldPassword.isNotEmpty()) {
                                    inputOldPassword =
                                        inputOldPassword.substring(0, inputOldPassword.length - 1)
                                    pinLength--
                                }
                            }

                            PasswordState.NEW_PASSWORD -> {
                                if (inputNewPassword.isNotEmpty()) {
                                    inputNewPassword =
                                        inputNewPassword.substring(0, inputNewPassword.length - 2)
                                    pinLength--
                                }
                            }

                            PasswordState.NEW_PASSWORD_RESET -> {
                                if (inputNewResetPassword.isNotEmpty()) {
                                    inputNewResetPassword = inputNewResetPassword.substring(
                                        0,
                                        inputNewResetPassword.length - 2
                                    )
                                    pinLength--
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun PinCodeScreenPreview() {
        PinCodeScreenContent()
    }
}