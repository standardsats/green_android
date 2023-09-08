package io.crypto.core.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.crypto.core.resources.R

val GramatikaFontFamily = FontFamily(
    Font(
        resId = R.font.gramatika_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.gramatika_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.gramatika_bold,
        weight = FontWeight.Bold
    )
)

/**
 * Regular ---------------------------------------------------
 */
val TextRegular = TextStyle(
    fontFamily = GramatikaFontFamily,
    fontWeight = FontWeight.Normal
)

val TextRegular_14_17_5 = TextRegular.copy(
    fontSize = 14.sp,
    lineHeight = 17.5.sp,
)

val TextRegular_14_17_5Grey = TextRegular_14_17_5.copy(
    color = Gray
)

val TextRegular_20_30 = TextRegular.copy(
    fontSize = 20.sp,
    lineHeight = 30.sp,
)

val TextRegular_20_30Dark = TextRegular_20_30.copy(
    color = Dark
)

val TextRegular_15_20 = TextRegular.copy(
    fontSize = 15.sp,
    lineHeight = 20.sp,
)

val TextRegular_15_20Dark = TextRegular_15_20.copy(
    color = Dark
)

val TextRegular_10_15 = TextRegular.copy(
    fontSize = 10.sp,
    lineHeight = 15.sp,
)

val TextRegular_10_15Dark = TextRegular_10_15.copy(
    color = Dark
)

val TextRegular_14 = TextRegular.copy(
    fontSize = 14.sp
)

val TextRegular_14Dark = TextRegular_14.copy(
    color = Dark
)

val TextRegular_19 = TextRegular.copy(
    fontSize = 19.sp
)

val TextRegular_19_White = TextRegular_19.copy(
    color = Color.White
)

val TextRegular_19_Dark = TextRegular_19.copy(
    color = Dark
)

/**
 * Medium ---------------------------------------------------
 */
val TextMedium = TextStyle(
    fontFamily = GramatikaFontFamily,
    fontWeight = FontWeight.Medium,
)

val TextMedium_14_15 = TextMedium.copy(
    fontSize = 14.sp,
    lineHeight = 15.sp,
)

val TextMedium_25_30 = TextMedium.copy(
    fontSize = 25.sp,
    lineHeight = 30.sp,
)

val TextMedium_25_30Dark = TextMedium_25_30.copy(
    color = Dark
)

val TextMedium_14_15Dark = TextMedium_14_15.copy(
    color = Dark
)

val TextMedium_37_50 = TextMedium.copy(
    fontSize = 37.sp,
    lineHeight = 50.sp,
)

val TextMedium40_50 = TextMedium.copy(
    fontSize = 40.sp,
    lineHeight = 50.sp
)

val TextMedium_37_50Dark = TextMedium_37_50.copy(
    color = Dark
)

val TextMedium20_50 = TextMedium.copy(
    fontSize = 20.sp,
    lineHeight = 50.sp
)

val TextMedium20_50Grey = TextMedium20_50.copy(
    color = LightGray
)

val TextMedium40_50Dark = TextMedium40_50.copy(
    color = Dark
)


/**
 * Bold ---------------------------------------------------
 */
val TextBold = TextStyle(
    fontFamily = GramatikaFontFamily,
    fontWeight = FontWeight.Bold
)

val TextBold_19 = TextBold.copy(
    fontSize = 19.sp,
)

val TextBold_19White = TextBold_19.copy(
    color = Color.White
)

val TextBold_19Blue = TextBold_19.copy(
    color = Blue
)

val TextBold_19Dark = TextBold_19.copy(
    color = Dark
)