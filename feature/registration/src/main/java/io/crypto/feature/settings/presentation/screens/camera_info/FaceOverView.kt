package io.crypto.feature.settings.presentation.screens.camera_info

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.crypto.feature.settings.utils.mainColor

@Composable
fun FaceOverView(modifier: Modifier = Modifier) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radiusX = 300f
        val radiusY = 370f

        drawOval(
            color = mainColor,


            topLeft = Offset(centerX - radiusX, centerY - radiusY),
            size = Size(radiusX * 2, radiusY * 2),
            style = Stroke(width = 2.dp.toPx(), join = StrokeJoin.Round, pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(20f, 10f)
            ))
        )
    }
}

@Preview
@Composable
fun FaceOverViewPrev() {
    FaceOverView()
}