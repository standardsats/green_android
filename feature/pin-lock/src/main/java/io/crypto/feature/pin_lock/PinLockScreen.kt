package io.crypto.feature.pin_lock

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.crypto.core.resources.R
import io.crypto.core.ui.theme.Background
import io.crypto.core.ui.theme.Dark
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.TextMedium40_50Dark
import io.crypto.core.ui.theme.TextRegular_14Dark
import io.crypto.feature.pin_lock.components.ActionKey
import io.crypto.feature.pin_lock.components.NumberKey
import io.crypto.feature.pin_lock.components.PinDots

@Composable
fun PinLockScreen(
    onBackClick: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: PinLockViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Icon(
            modifier = Modifier
                .padding(16.dp)
                .clickable(onClick = onBackClick),
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            tint = Dark,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(32.dp)
        ) {
            Text(
                text = "Setting PIN-code",
                style = TextMedium40_50Dark
            )

            Text(
                text = "You will need a pin-code to enter your wallet. This pin-code only protects the wallet on your device",
                style = TextRegular_14Dark
            )

            PinDots(
                modifier = Modifier.padding(top = 48.dp),
                enteredDigitsCount = uiState.password.length
            )
        }

        KeyBoard(
            onNumberPressed = viewModel::onNumberPressed,
            onDeletePressed = viewModel::onDelete
        )
    }
}

@Composable
private fun KeyBoard(
    onNumberPressed: (value: Int) -> Unit,
    onDeletePressed: () -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(16.dp),
        columns = GridCells.Fixed(3),
    ) {
        items(9) {
            val value = it + 1
            NumberKey(value = value, onPressed = onNumberPressed)
        }

        // Empty item before zero
        item {}

        item {
            NumberKey(
                value = 0,
                onPressed = onNumberPressed
            )
        }

        item {
            ActionKey(onPressed = onDeletePressed)
        }
    }
}

@Preview
@Composable
fun PinLockScreen_Preview() {
    SuperAppTheme {
        Surface {
            PinLockScreen(
                onBackClick = {},
                onSuccess = {}
            )
        }
    }
}