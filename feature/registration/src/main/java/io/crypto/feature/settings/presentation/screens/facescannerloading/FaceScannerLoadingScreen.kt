package io.crypto.feature.settings.presentation.screens.facescannerloading

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import io.crypto.feature.settings.utils.mainColor

class FaceScannerLoadingScreen : AppScreen() {
    @Composable
    override fun Content() {
        LoadingScreenContent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoadingScreenContent() {
        val viewModel = getViewModel<FaceScannerLoadScreenViewModel>()
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Scaffold(topBar = {
                MyTopBar(icon = R.drawable.ic_arrow_back) {
                    viewModel.back()
                }
            }) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_scanner_loading),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(top = 32.dp),
                        text = "Your identity is being authenticated",
                        style = TextStyle(
                            fontSize = 25.sp,
                            lineHeight = 30.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF09282D),
                            textAlign = TextAlign.Center,
                        )
                    )
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "You'll be redirected shortly...",
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF09282D),
                            textAlign = TextAlign.Center,
                        )
                    )

                    Row(
                        modifier = Modifier.padding(top = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = mainColor,
                            strokeWidth = 4.dp,
                            modifier = Modifier.size(26.dp)
                        )
                        Text(
                            text = "Sending your data...",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 17.5.sp,
//                                fontFamily = FontFamily(Font(R.font.roboto)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF67728D),
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun LoadingPreview() {
        LoadingScreenContent()
    }

}