package io.crypto.feature.capture.video.capture_result

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import io.crypto.core.resources.R
import io.crypto.core.ui.components.SuperGradientButton
import io.crypto.core.ui.components.SuperOutlinedButton
import io.crypto.core.ui.theme.Dark
import io.crypto.core.ui.theme.TextMedium_25_30Dark
import io.crypto.core.ui.theme.TextRegular_15_20Dark

@Composable
fun VideoPreviewScreen(
    onSendVerificationClick: () -> Unit,
    onTakeAnotherVideoClick: () -> Unit,
    viewModel: VideoPreviewViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uiState.videoUri))
            prepare()
        }
    }

    DisposableEffect(
        uiState.videoUri.also {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(horizontal = 16.dp),
            ) {
                Icon(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .clickable(onClick = onTakeAnotherVideoClick),
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null,
                    tint = Dark,
                )

                VideoPreviewContent(
                    exoPlayer = exoPlayer,
                    onSendVerificationClick = onSendVerificationClick,
                    onTakeAnotherVideoClick = onTakeAnotherVideoClick
                )
            }
        }
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}

@Composable
fun VideoPreviewContent(
    exoPlayer: ExoPlayer,
    onSendVerificationClick: () -> Unit,
    onTakeAnotherVideoClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            factory = { context ->
                StyledPlayerView(context).apply {
                    player = exoPlayer
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1 / 1.5f),
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = R.string.video),
            style = TextMedium_25_30Dark,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = stringResource(id = R.string.make_sure_face_visible),
            style = TextRegular_15_20Dark,
            textAlign = TextAlign.Center
        )

        SuperGradientButton(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = R.string.send_verification),
            onClick = onSendVerificationClick
        )

        SuperOutlinedButton(
            modifier = Modifier.padding(top = 12.dp),
            text = stringResource(id = R.string.take_another_video),
            onClick = onTakeAnotherVideoClick
        )
    }
}