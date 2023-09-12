package io.crypto.feature.camera.capture.camera

import android.content.Context
import android.graphics.Bitmap
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import io.crypto.core.resources.R
import io.crypto.core.ui.theme.TextMedium_25_30Dark
import io.crypto.core.ui.theme.TextRegular_15_30Dark

@Composable
fun CameraScreen(
    onBackClick: () -> Unit,
    onCapture: (filePath: String) -> Unit,
    viewModel: CameraViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()

    when (uiState.loading) {
        true -> LoadingState()
        false -> CameraState(onBackClick) {
            viewModel.onPhotoCaptured(context, it)
        }
    }

    LaunchedEffect(uiState.capturedImageFilePath) {
        val path = uiState.capturedImageFilePath
        path ?: return@LaunchedEffect
        onCapture(path)
        viewModel.onPhotoConsumed()
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CameraState(
    onBackClick: () -> Unit,
    onPhotoCaptured: (Bitmap) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Icon(
            modifier = Modifier
                .padding(16.dp)
                .clickable(onClick = onBackClick),
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
        )

        CameraContentMain(
            lifecycleOwner = lifecycleOwner,
            cameraController = cameraController
        )

        CameraContentSuggestion(
            context = context,
            cameraController = cameraController,
            onPhotoCaptured = onPhotoCaptured
        )
    }
}

@Composable
private fun BoxScope.CameraContentMain(
    lifecycleOwner: LifecycleOwner,
    cameraController: LifecycleCameraController
) {
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter),
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxHeight(0.85f)
                .fillMaxWidth(),
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    setBackgroundColor(Color.Black.toArgb())
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleType = PreviewView.ScaleType.FILL_START
                    controller = cameraController
                    cameraController.bindToLifecycle(lifecycleOwner)
                }
            }
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )
                )
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun BoxScope.CameraContentSuggestion(
    context: Context,
    cameraController: LifecycleCameraController,
    onPhotoCaptured: (Bitmap) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .align(Alignment.BottomCenter),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FloatingActionButton(
            modifier = Modifier
                .border(
                    border = BorderStroke(3.dp, Color.White),
                    shape = CircleShape
                )
                .padding(5.dp),
            containerColor = Color.White,
            shape = CircleShape,
            onClick = {
                val mainExecutor = ContextCompat.getMainExecutor(context)

                cameraController.takePicture(
                    mainExecutor,
                    object : ImageCapture.OnImageCapturedCallback() {
                        override fun onCaptureSuccess(image: ImageProxy) {

                            val correctedBitmap: Bitmap = image
                                .toBitmap()

                            onPhotoCaptured(correctedBitmap)

                            image.close()
                        }

                        override fun onError(exception: ImageCaptureException) {
                            // TODO Timber Log
                        }
                    }
                )
            },
            content = {}
        )

        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.front_side_document),
                style = TextMedium_25_30Dark,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(id = R.string.scan_front_document),
                style = TextRegular_15_30Dark,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun Preview_CameraContent() {
    CameraState(
        onBackClick = {},
        onPhotoCaptured = {}
    )
}