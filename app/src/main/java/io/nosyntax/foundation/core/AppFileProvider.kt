package io.nosyntax.foundation.core

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class AppFileProvider : FileProvider() {
    companion object {
        fun getUriForFile(context: Context, uri: Uri): Uri? {
            return try {
                val file = File(uri.path ?: return null)
                val authority = "${context.packageName}.provider"
                val fileUri = getUriForFile(context, authority, file)

                fileUri
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        suspend fun writeUriToFile(context: Context, directory: File, uri: Uri): Uri? {
            if (!directory.exists()) {
                directory.mkdir()
            }

            val fileName = DocumentFile.fromSingleUri(context, uri)?.name
            val outputFile = File(directory, fileName ?: return null)

            return try {
                val inputStream = context.contentResolver.openInputStream(uri) ?: return null
                val outputStream = withContext(Dispatchers.IO) {
                    FileOutputStream(outputFile)
                }

                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }

                Uri.fromFile(outputFile)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}