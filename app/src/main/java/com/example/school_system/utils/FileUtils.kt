package com.example.school_system.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun uriToFile(uri: Uri, context: Context): File {
    val inputStream = context.contentResolver.openInputStream(uri)!!
    val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
    val outputStream = FileOutputStream(tempFile)

    inputStream.copyTo(outputStream)
    outputStream.close()
    inputStream.close()

    return tempFile
}