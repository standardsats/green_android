package io.crypto.feature.settings.presentation.screens.send

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import io.crypto.feature.registration.R
import io.crypto.feature.settings.navigation.AppScreen
import io.crypto.feature.settings.presentation.components.MyTopBar
import io.crypto.feature.settings.utils.mainColor

class SendScreen : AppScreen() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        SendScreenContent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SendScreenContent() {
        val viewModel = getViewModel<SendScreenViewModel>()

        var valueText by remember {
            mutableStateOf("")
        }
        var feeValue by remember {
            mutableDoubleStateOf(0.01)
        }


        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFBFC))
        ) {
            Scaffold(modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFBFC)), topBar = {
                MyTopBar(text = "Send") {
                    viewModel.back()
                }
            }) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {

                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp))
                            .background(Color.White)
                            .padding(horizontal = 16.dp)
                            .padding(top = 40.dp)
                    ) {
                        Text(
                            text = "Recipient",
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF09282D),
                            )
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 14.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_avatar),
                                contentDescription = null
                            )
                            Column(modifier = Modifier.padding(start = 24.dp)) {
                                Text(
                                    text = "Merchant Name",
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF09282D),
                                    )
                                )
                                Text(
                                    text = "Geo",
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        lineHeight = 17.5.sp,
                                        fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFA4B4CB),
                                    )
                                )
                            }
                            Spacer(
                                modifier = Modifier
                                    .width(0.dp)
                                    .weight(1f)
                            )
                            Text(
                                text = "Food",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 25.sp,
                                    fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF71A600),
                                    textAlign = TextAlign.Right,
                                )
                            )
                        }


                        Spacer(modifier = Modifier.height(30.dp))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(32.dp))
                            .background(Color.White)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Sum",
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF09282D),
                            ),
                            modifier = Modifier.padding(start = 0.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White),
                                value = if (valueText.isEmpty()) valueText else valueText + " eTHB",

                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.White,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                ),
//                                singleLine = true,
                                onValueChange = { txt ->
                                    valueText = if (txt.length > 4) txt.substring(
                                        0,
                                        txt.length - 5
                                    ) else txt
                                }, textStyle = TextStyle(
                                    fontSize = 36.sp,
                                    fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF09282D),
                                    textAlign = TextAlign.Center,
                                )
                            )
                            /* Text(
                                 text = "100",
                                 style = TextStyle(
                                     fontSize = 40.sp,
                                     fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                     fontWeight = FontWeight(400),
                                     color = Color(0xFF09282D),
                                     textAlign = TextAlign.Center,
                                 )
                             )
                             Spacer(modifier = Modifier.width(16.dp))
                             Text(
                                 text = "eTHB",
                                 style = TextStyle(
                                     fontSize = 24.sp,
                                     fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                     fontWeight = FontWeight(400),
                                     color = mainColor,
                                     textAlign = TextAlign.Center,
                                 )
                             )*/
                        }
                        Box(
                            modifier = Modifier
                                .border(width = 1.dp, color = Color(0xFFDFE5F4))
                                .padding(1.dp)
                                .width(338.dp)
                                .height(0.dp)
                                .align(Alignment.CenterHorizontally),
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = "Fee: ",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF09282D),
                                )
                            )
                            Text(
                                text = " $feeValue eTHB",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                                    fontWeight = FontWeight(400),
                                    color = mainColor,
                                )
                            )
                        }


                    }
                    Spacer(
                        modifier = Modifier
                            .height(0.dp)
                            .weight(5f)
                    )

                    Button(
                        onClick = { viewModel.openInfoScreen() },
                        modifier = Modifier
                            .padding(horizontal = 18.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = mainColor,
                            containerColor = mainColor
                        )
                    ) {
                        Text(
                            text = "Send",
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
                            .weight(3f)
                    )
                }

            }
        }
    }

    @Preview
    @Composable
    fun SendScreenPreview() {
        SendScreenContent()
    }

}

