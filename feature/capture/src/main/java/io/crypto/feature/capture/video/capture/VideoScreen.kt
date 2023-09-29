package io.crypto.feature.capture.video.capture

import android.content.Context
import android.net.Uri
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.camera.core.CameraSelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import io.crypto.core.resources.R
import io.crypto.core.ui.theme.Dark
import io.crypto.core.ui.theme.TextMedium_25_30Dark
import io.crypto.core.ui.theme.TextRegular_15_30Dark
import io.crypto.feature.capture.utils.createVideoCaptureUseCase
import io.crypto.feature.capture.utils.startRecordingVideo
import java.io.File

@Composable
fun VideoScreen(
    onBackClick: () -> Unit,
    onCapture: (filePath: String) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var previewView: PreviewView = remember { PreviewView(context) }
    var recording: MutableState<Recording?> = remember { mutableStateOf(null) }
    var videoCapture: MutableState<VideoCapture<Recorder>?> = remember { mutableStateOf(null) }
    var isRecording = remember { mutableStateOf(false) }
    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_FRONT_CAMERA) }

    LaunchedEffect(key1 = Unit) {
        videoCapture.value = context.createVideoCaptureUseCase(
            lifecycleOwner,
            cameraSelector,
            previewView
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        VideoContentMain(
            onBackClick = onBackClick,
            previewView = previewView
        )

        VideoContentSuggestion(
            isRecording = isRecording,
            videoCapture = videoCapture,
            recording = recording,
            context = context,
            onCapture = onCapture
        )
    }
}

@Composable
private fun BoxScope.VideoContentMain(
    onBackClick: () -> Unit,
    previewView: PreviewView
) {
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter),
    ) {
        Icon(
            modifier = Modifier
                .padding(16.dp)
                .clickable(onClick = onBackClick)
                .align(Alignment.TopStart),
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            tint = Dark,
        )

        AndroidView(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(),
            factory = {
                previewView.apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    setBackgroundColor(Color.Black.toArgb())
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleType = PreviewView.ScaleType.FILL_START
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
private fun BoxScope.VideoContentSuggestion(
    isRecording: MutableState<Boolean>,
    videoCapture: MutableState<VideoCapture<Recorder>?>,
    recording: MutableState<Recording?>,
    context: Context,
    onCapture: (filePath: String) -> Unit
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
                .border(BorderStroke(3.dp, Color.White), CircleShape)
                .padding(5.dp),
            containerColor = if (isRecording.value.not()) Color.White else Color.Red,
            shape = CircleShape,
            onClick = {
                if (isRecording.value.not()) {
                    videoCapture.value?.let { videoCapture ->
                        isRecording.value = true

                        val mediaDir = context.externalCacheDirs.firstOrNull()?.let {
                            File(it, "verification").apply { mkdirs() }
                        }

                        recording.value = startRecordingVideo(
                            context = context,
                            filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                            videoCapture = videoCapture,
                            outputDirectory = if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir,
                            executor = ContextCompat.getMainExecutor(context),
                            audioEnabled = false,
                        ) { event ->
                            if (event is VideoRecordEvent.Finalize) {
                                val uri = event.outputResults.outputUri
                                if (uri != Uri.EMPTY) {
                                    onCapture(uri.path!!)
                                }
                            }
                        }
                    }
                } else {
                    recording.value?.stop()
                    isRecording.value = false
                }
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
                text = stringResource(id = R.string.make_video),
                style = TextMedium_25_30Dark,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(id = R.string.make_verification_video),
                style = TextRegular_15_30Dark,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun Preview_VideoContent() {
    VideoScreen(
        onBackClick = {},
        onCapture = {}
    )
}