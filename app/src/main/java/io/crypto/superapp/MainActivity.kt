package io.crypto.superapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import io.crypto.core.ui.theme.SuperAppTheme
import io.crypto.superapp.ui.SuperApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setUpAppLanguage()
        super.onCreate(savedInstanceState)
        setContent {
            SuperAppTheme {
                SetStatusBarColor(color = Color.White) {
                    SuperApp()
                }
            }
        }
    }

    private fun setUpAppLanguage() {
        /*TODO*/
    }

    @Composable
    fun SetStatusBarColor(color: Color, content: @Composable () -> Unit) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(color)
        }

        content()
    }
}