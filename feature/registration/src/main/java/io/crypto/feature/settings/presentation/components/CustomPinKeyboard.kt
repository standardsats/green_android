package io.crypto.feature.settings.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
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
import io.crypto.feature.registration.R

@Composable
fun CustomKeyboard(onClickNumber:(String) -> Unit, clickDeleteBtn:() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFAFBFC))
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 32.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFEDF0F7),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .width(0.dp)
                .weight(1f)
                .clickable {
                    onClickNumber("1")
                }
                .padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = "1",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2E506E),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFEDF0F7),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .width(0.dp)
                .weight(1f)
                .clickable {
                    onClickNumber("2")
                }
                .padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = "2",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2E506E),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFEDF0F7),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .width(0.dp)
                .weight(1f)
                .clickable {
                    onClickNumber("3")
                }
                .padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = "3",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2E506E),
                        textAlign = TextAlign.Center,
                    )
                )
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFEDF0F7),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .width(0.dp)
                .weight(1f)
                .clickable {
                    onClickNumber("4")
                }
                .padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = "4",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2E506E),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFEDF0F7),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .width(0.dp)
                .weight(1f)
                .clickable {
                    onClickNumber("5")
                }
                .padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = "5",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2E506E),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFEDF0F7),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .width(0.dp)
                .weight(1f)
                .clickable {
                    onClickNumber("6")
                }
                .padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = "6",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2E506E),
                        textAlign = TextAlign.Center,
                    )
                )
            }

        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFEDF0F7),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .width(0.dp)
                .weight(1f)
                .clickable {
                    onClickNumber("7")
                }
                .padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = "7",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2E506E),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFEDF0F7),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .width(0.dp)
                .weight(1f)
                .clickable {
                    onClickNumber("8")
                }
                .padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = "8",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2E506E),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFEDF0F7),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .width(0.dp)
                .weight(1f)
                .clickable {
                    onClickNumber("9")
                }
                .padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = "9",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2E506E),
                        textAlign = TextAlign.Center,
                    )
                )
            }

        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier

                .width(0.dp)
                .weight(1f)

                .padding(vertical = 10.dp), contentAlignment = Alignment.Center) {

            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color(0xFFEDF0F7),
                    shape = RoundedCornerShape(size = 4.dp)
                )
                .width(0.dp)
                .weight(1f)
                .clickable {
                    onClickNumber("0")
                }
                .padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                Text(
                    text = "0",
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = FontFamily(Font(R.font.gramatika_medium)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2E506E),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier

                .width(0.dp)
                .weight(1f)
                .clickable {

                }
                .padding(vertical = 10.dp), contentAlignment = Alignment.Center) {
                IconButton(onClick = { clickDeleteBtn() }) {
                    Image(painter = painterResource(id = R.drawable.ic_keyboard_delete), contentDescription =null )
                }
            }

        }
    }
}

@Preview
@Composable
fun CustomKeyboardPrev() {
    CustomKeyboard({}){}
}