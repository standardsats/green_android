package io.crypto.feature.settings.presentation.screens.qrscanner

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import io.crypto.feature.registration.R
import io.crypto.feature.settings.navigation.AppScreen
import io.crypto.feature.settings.presentation.components.CommonTextField
import io.crypto.feature.settings.utils.QrCodeAnalyzer

class QRScannerScreen : AppScreen() {
    @Composable
    override fun Content() {
        var code by remember {
            mutableStateOf("")
        }
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val cameraProviderFuture = remember {
            ProcessCameraProvider.getInstance(context)
        }
        var hasCamPermission by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            )
        }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { granted ->
                hasCamPermission = granted
            }
        )
        LaunchedEffect(key1 = true) {
            launcher.launch(Manifest.permission.CAMERA)
        }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (hasCamPermission) {
                Box(modifier = Modifier.fillMaxSize()) {

                    AndroidView(
                        factory = { context ->
                            val previewView = PreviewView(context)

                            val preview = Preview.Builder().build()

                            val selector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()
                            preview.setSurfaceProvider(previewView.surfaceProvider)
                            val imageAnalysis = ImageAnalysis.Builder()
                                .setTargetResolution(
                                    Size(
                                        previewView.width,
                                        previewView.height
                                    )
                                )
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()

                            imageAnalysis.setAnalyzer(
                                ContextCompat.getMainExecutor(context),
                                QrCodeAnalyzer { result ->
                                    code = result
                                }
                            )
                            try {
                                cameraProviderFuture.get().bindToLifecycle(
                                    lifecycleOwner,
                                    selector,
                                    preview,
                                    imageAnalysis
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                            previewView
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                    CV(modifier = Modifier.align(Alignment.Center))
                    if (code.isNotEmpty()) {
                        Box(
                            modifier = Modifier

                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                                .background(Color.White)
                        ) {

                            Column(modifier = Modifier.fillMaxWidth()) {
                                Spacer(modifier = Modifier.height(16.dp))
                                CommonTextField(
                                    code,
                                    "Address manual entry",
                                    {},
                                    KeyboardOptions.Default
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 32.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {

                                    Image(
                                        modifier = Modifier.size(32.dp),
                                        painter = painterResource(id = R.drawable.ic_copy),
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = "Paste from clipboard",
                                        style = TextStyle(
                                            fontSize = 20.sp,
                                            lineHeight = 50.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF09282D),
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 32.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {

                                    Image(
                                        modifier = Modifier.size(32.dp),
                                        painter = painterResource(id = R.drawable.ic_pic),
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = "Load image from disk", style = TextStyle(
                                            fontSize = 20.sp,
                                            lineHeight = 50.sp,
                                            fontFamily = FontFamily(Font(R.font.gramatika_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF09282D),
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }

                Text(
                    text = code,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                )
            }
        }
    }

    @Composable
    fun CV(modifier: Modifier) {
        var scale: Float = 1.0F
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
//        Image(
//            modifier = Modifier
//                .align(Alignment.Center)
////                .graphicsLayer(
////                    scaleX = maxOf(.2f, minOf(5f, scale)),
////                    scaleY = maxOf(.2f, minOf(5f, scale))
////                )
//            ,
//            painter = painterResource(id = R.drawable.ic_launcher_background),
//            contentDescription = null
//        )

            Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
//                drawOval(color = mainColor, size = androidx.compose.ui.geometry.Size(50f, 100f))
                val circlePath = Path().apply {
                    addOval(Rect(center, size.minDimension / 3))
                }
//            clipPath(circlePath, clipOp = ClipOp.Difference) {
//                drawRect(SolidColor(Color.Black.copy(alpha = 0.8f)))
//            }

                val rectanglePath = Path().apply {
                    addRoundRect(
                        RoundRect(
                            rect = Rect(
                                center, 100.dp.toPx()
                            ),
                            cornerRadius = CornerRadius(20f, 20f)
                        )
                    )
                }

                val rectanglePath2 = Path().apply {
                    addOval(Rect(center, 100.dp.toPx()))
                    /*addRoundRect(
                        RoundRect(
                            rect = Rect(
                                Offset(center.x + 20, center.y + 20), 80.dp.toPx()
                            ),
                            cornerRadius = CornerRadius(20f, 20f)
                        )
                    )*/
                }

                clipPath(rectanglePath, clipOp = ClipOp.Difference) {
                    drawRoundRect(SolidColor(Color.Black.copy(alpha = 0.8f)))
                }

//            drawPath(rectanglePath2, SolidColor(Color.Black.copy(alpha = 0.8f)))

//            clipPath(rectanglePath2, clipOp = ClipOp.Difference) {
//                drawRoundRect(SolidColor(Color.Blue.copy(alpha = 0.5f)))
//            }
            })
        }
    }

}