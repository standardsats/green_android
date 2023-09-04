package io.crypto.core.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.crypto.core.resources.R

// need to replace with [Gramatika]
val RobotoFontFamily = FontFamily(
    Font(
        resId = R.font.roboto_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.roboto_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.roboto_bold,
        weight = FontWeight.Bold
    )
)

val TextMedium = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Medium
)

val TextMedium20_50 = TextMedium.copy(
    fontSize = 20.sp,
    lineHeight = 50.sp
)

val TextMedium20_50Grey = TextMedium20_50.copy(
    color = LightGray
)

val TextRegular = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Normal
)

val TextBold = TextStyle(
    fontFamily = RobotoFontFamily,
    fontWeight = FontWeight.Bold
)

val TextBold40_50 = TextBold.copy(
    fontSize = 40.sp,
    lineHeight = 50.sp
)

val TextBold40_50Dark = TextBold40_50.copy(
    color = Dark
)

val TextRegular_W700 = TextRegular.copy(
    fontWeight = FontWeight(700)
)

val TextRegular_W700_19 = TextRegular_W700.copy(
    fontSize = 19.sp
)

val TextRegular_W700_19_White = TextRegular_W700_19.copy(
    color = Color.White
)

val TextRegular_W700_19_Dark = TextRegular_W700_19.copy(
    color = Dark
)
