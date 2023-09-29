package io.crypto.feature.settings.presentation.screens.camera_info

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
import io.crypto.feature.settings.utils.mainColor

class CameraInfoScreen : AppScreen(){
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = getViewModel<CameraInfoViewModel>()
        Surface(modifier = Modifier.fillMaxSize()) {
            Scaffold(topBar = { MyTopBar(){
                viewModel.back()
            } }, modifier = Modifier.background(Color.White)) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(it)
                    .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = Modifier.padding(top = 24.dp),
                        text = "To confirm your entry, you need your full-face photo, as in the picture below",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF09282D),
                        )
                    )
                    Spacer(modifier = Modifier
                        .height(0.dp)
                        .weight(1f))
                    Image(painter = painterResource(id = R.drawable.ic_face), contentDescription = null)
                    Spacer(modifier = Modifier
                        .height(0.dp)
                        .weight(1f))
                    Button(
                        onClick = {
                                  viewModel.openFaceScannerScreen()
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(contentColor = mainColor, containerColor = mainColor)
                    ) {
                        Text(
                            text = "I'm ready to take a photo",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_bold)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            )
                        )

                    }
                    Spacer(modifier = Modifier
                        .height(0.dp)
                        .weight(1f))
                }
            }
        }
    }

    @Preview
    @Composable
    fun CameraInfoPrev() {
        Content()
    }
}
