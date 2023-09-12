package io.crypto.feature.camera.capture.no_permission

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.crypto.core.ui.components.SuperTextButton
import io.crypto.core.ui.theme.DarkTurquoise
import io.crypto.core.ui.theme.TextRegular_15_15DarkTurquoise
import io.crypto.core.ui.theme.TextRegular_15_15DogwoodRose
import io.crypto.core.ui.theme.TextRegular_18_21Dark
import io.crypto.feature.camera.R

@Composable
fun NoPermissionsScreen(
    onRequestPermission: () -> Unit,
    onReject: () -> Unit
) {
    NoPermissionContent(
        onRequestPermission,
        onReject
    )
}

@Composable
private fun NoPermissionContent(
    onRequestPermission: () -> Unit,
    onReject: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp),
                )
                .border(
                    border = BorderStroke(1.dp, Color.DarkGray.copy(0.1f)),
                    shape = RoundedCornerShape(12.dp),
                )
                .padding(vertical = 36.dp, horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null,
                tint = DarkTurquoise
            )
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = "Allow Thai digital wallet app to access camera?",
                style = TextRegular_18_21Dark,
                textAlign = TextAlign.Center
            )
            SuperTextButton(
                modifier = Modifier.padding(top = 14.dp),
                text = "Allow",
                onClick = onRequestPermission,
                style = TextRegular_15_15DarkTurquoise
            )
            SuperTextButton(
                text = "Reject",
                onClick = onReject,
                style = TextRegular_15_15DogwoodRose
            )
        }
    }
}