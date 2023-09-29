package io.crypto.feature.settings.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.crypto.feature.registration.R

@Composable
fun TransactionItem(name: String, categoryName: String, amount: String, imageUrl: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        //todo Network image
        Image(painter = painterResource(id = R.drawable.ic_clock), contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = name,
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 50.sp,
                    fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF09282D),
                )
            )
            Text(
                text = categoryName,
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 50.sp,
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
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxHeight()) {
            Text(
                text = amount,
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 50.sp,
                    fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFA4B4CB),
                    textAlign = TextAlign.Right,
                )
            )

            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = Color(0xFFDDE3F3))
                    .padding(1.dp)
                    .width(109.dp)
                    .height(0.dp)
            )
        }
    }
}