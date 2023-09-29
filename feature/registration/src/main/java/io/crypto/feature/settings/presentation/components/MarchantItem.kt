package io.crypto.feature.settings.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun MerchantItem(name: String, geoName: String, categoryName: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        //todo Network image
        Image(
            painter = painterResource(id = R.drawable.ic_circular_picture),
            modifier = Modifier.size(50.dp),
            contentDescription = null
        )
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
                text = geoName,
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
        Column(horizontalAlignment = Alignment.End, modifier = Modifier) {
            Text(
                text = categoryName,
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 50.sp,
                    fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                    fontWeight = FontWeight(400),
                    color = color,
                    textAlign = TextAlign.Right,
                )
            )

        }
    }
}