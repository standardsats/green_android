package io.crypto.feature.camera.utils

import android.content.Context
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import java.io.File

fun Bitmap.createFile(context: Context, fileName: String = "doc.png"): File {
    return File(context.externalCacheDir, fileName).apply {
        createNewFile()
        outputStream().use {
            this@createFile.compress(
                Bitmap.CompressFormat.PNG,
                100,
                it
            )
        }
    }
}

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}