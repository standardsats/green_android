package io.crypto.feature.settings.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(
    text: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        label = { Text(text = placeholder, color = Color.Gray) },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.outlinedTextFieldColors(

            focusedBorderColor = Color(0xFF1BBEC1),
            cursorColor = Color.Black,
            unfocusedBorderColor = Color(0xFF1BBEC1),
            focusedLabelColor = Color(0xFF1BBEC1),
            disabledLabelColor = Color(0xFF1BBEC1),
            unfocusedLabelColor = Color(0xFF1BBEC1),
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth(),
        visualTransformation = visualTransformation
    )
}