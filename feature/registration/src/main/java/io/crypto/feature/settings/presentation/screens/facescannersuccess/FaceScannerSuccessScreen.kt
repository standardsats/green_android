package io.crypto.feature.settings.presentation.screens.facescannersuccess

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import io.crypto.feature.registration.R
import io.crypto.feature.settings.navigation.AppScreen
import io.crypto.feature.settings.presentation.components.MyTopBar

class FaceScannerSuccessScreen : AppScreen() {
    @Composable
    override fun Content() {
        SuccessScreenContent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SuccessScreenContent() {
        val viewModel = getViewModel<FaceScannerSuccessScreenViewModel>()
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Scaffold(topBar = {
                MyTopBar(icon = R.drawable.ic_arrow_back_to_top) {
                    viewModel.backToHome()
                }
            }) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(0.dp)
                            .weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_scanner_success),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(top = 32.dp),
                        text = "Transaction was successful",
                        style = TextStyle(
                            fontSize = 25.sp,
                            lineHeight = 30.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF09282D),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Spacer(
                        modifier = Modifier
                            .height(0.dp)
                            .weight(2f)
                    )
                    Button(
                        onClick = { viewModel.backToHome() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color(0xFF13C4D0),
                            containerColor = Color(0xFF13C4D0)
                        )
                    ) {
                        Text(
                            text = "Go back to the main page",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_bold)),
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

    @Preview
    @Composable
    fun SuccessPreview() {
        SuccessScreenContent()
    }

}