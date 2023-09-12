package io.crypto.feature.navigation.result

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import io.crypto.feature.navigation.Constants.NAV_BACK_RESULT

fun <R> NavController.navigateBack(result: R) {
    previousBackStackEntry?.savedStateHandle?.let {
        it[NAV_BACK_RESULT] = result
    }
    navigateUp()
}

@SuppressLint("ComposableNaming")
@Composable
fun <R> NavController.onNavResult(listener: @DisallowComposableCalls (R) -> Unit) {
    if (currentBackStackEntry == null) {
        return
    }

    val currentListener by rememberUpdatedState(listener)

    DisposableEffect(currentBackStackEntry) {
        val observer = object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_START,
                    Lifecycle.Event.ON_RESUME -> {
                        handleResultIfPresent(currentListener)
                    }

                    Lifecycle.Event.ON_DESTROY -> {
                        currentBackStackEntry!!.lifecycle.removeObserver(this)
                    }

                    else -> Unit
                }
            }
        }

        currentBackStackEntry!!.lifecycle.addObserver(observer)

        onDispose {
            currentBackStackEntry!!.lifecycle.removeObserver(observer)
        }
    }
}

private fun <R> NavController.handleResultIfPresent(listener: (R) -> Unit) {
    if (currentBackStackEntry!!.savedStateHandle.contains(NAV_BACK_RESULT)) {
        listener(
            currentBackStackEntry!!.savedStateHandle.remove<R>(NAV_BACK_RESULT) as R
        )
    }
}
