package io.crypto.feature.settings.presentation.screens.enviorment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import io.crypto.feature.registration.R
import io.crypto.feature.settings.navigation.AppScreen
import io.crypto.feature.settings.utils.mainColor

class EnvironmentScreen : AppScreen() {
    @Composable
    override fun Content() {
        EnvironmentScreenContent()
    }

    @Composable
    private fun EnvironmentScreenContent() {
        val viewModel = getViewModel<EnvironmentScreenViewModel>()
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFBFC))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFAFBFC))
            ) {
                IconButton(onClick = { viewModel.backToSettings() }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(0.dp)
                        .weight(1f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(Color.White)

                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_home),
                        contentDescription = null
                    )
                    Text(
                        text = "Safe environment",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF09282D),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Text(
                        text = "Make sure you are alone and no camera is recording you or the screen",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF09282D),
                            textAlign = TextAlign.Center,
                        )
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(0.dp)
                        .weight(0.5f)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(32.dp))
                        .background(Color.White)

                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_shield),
                        contentDescription = null
                    )
                    Text(
                        text = "Secure storage",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF09282D),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Text(
                        text = "Write down the 12-digit code and keep it in a safe place",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF09282D),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(0.dp)
                        .weight(1f)
                )

                Button(
                    onClick = { viewModel.openStoreScreen() },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(mainColor)
                ) {
                    Text(
                        text = "Show recovery phrase",
                        style = TextStyle(
                            fontSize = 19.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(0.dp)
                        .weight(1f)
                )
            }
        }
    }
}