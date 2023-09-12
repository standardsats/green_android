package io.crypto.feature.registration.recovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.crypto.core.resources.R
import io.crypto.core.ui.components.SuperGradientButton
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.core.ui.theme.TextMedium40_50Dark
import io.crypto.core.ui.theme.TextRegular_14Dark

@Composable
fun RecoveryScreen(
    onNextButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            modifier = Modifier.padding(top = 38.dp)
        )

        Text(
            text = stringResource(id = R.string.enter_recovery_phrase),
            style = TextMedium40_50Dark,
            modifier = Modifier.padding(top = 38.dp, start = 7.dp, end = 6.dp)
        )

        Text(
            modifier = Modifier.padding(top = 10.dp, start = 8.dp),
            text = stringResource(id = R.string.provided_registration),
            style = TextRegular_14Dark,
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(
                start = 15.dp,
                end = 15.dp,
                bottom = 38.dp
            ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
//            itemsIndexed(state.styleList) { index, item ->
//
//                RecoveryItemStyleView.Default(
//                    data = item,
//                    onClick = { listeners.onSelectStyle(index) },
//                    isSelected = state.styleIndex.value == index
//                )
//            }
        }

        Spacer(modifier = Modifier.weight(1f))

        SuperGradientButton(
            modifier = Modifier
                .padding(bottom = 130.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.next),
            onClick = onNextButtonClick
        )
    }
}

@Preview
@Composable
fun RecoveryScreen() {
    SuperAppTheme {
        Surface {
            RecoveryScreen(
                onNextButtonClick = {},
            )
        }
    }
}