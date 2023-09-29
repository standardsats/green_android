package io.crypto.feature.settings.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.crypto.feature.registration.R

@Composable
fun MyTopBar(
    elevation: Int = 0,
    @DrawableRes icon: Int = R.drawable.ic_arrow_back_to_top,
    text: String = "",
    back: () -> Unit
) {
    Surface(
        shadowElevation = elevation.dp,
        color = Color.White,
        modifier = Modifier.background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                back()
            }, modifier = Modifier
                .size(44.dp)
                .padding(8.dp)) {
                Icon(painter = painterResource(id = icon), contentDescription = text)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = text,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF09282D),
                )
            )
        }
    }
    /*TopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White, Color.White),
            title = {
                Row(modifier = Modifier.fillMaxHeight()) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = text,
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF09282D),
                        )
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = {
                    //todo
                }, modifier = Modifier.padding(8.dp)) {
                    Icon(painter = painterResource(id = icon), contentDescription = text)
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(0.dp, 0.dp, 0.dp, 8.dp),
        )*/
}

@Preview
@Composable
fun TopBarPrev() {
    MyTopBar(text = "Send") {

    }
}