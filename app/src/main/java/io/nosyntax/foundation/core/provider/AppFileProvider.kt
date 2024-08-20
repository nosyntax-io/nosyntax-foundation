package io.nosyntax.foundation.core.provider

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
            return runCatching {
                val file = File(uri.path.orEmpty())
                val authority = "${context.packageName}.provider"
                getUriForFile(context, authority, file)
            }.getOrElse { exception ->
                exception.printStackTrace()
                null
            }
        }

        suspend fun writeUriToFile(context: Context, directory: File, uri: Uri): Uri? {
            if (!directory.exists() && !directory.mkdir()) {
                return null
            }

            val fileName = DocumentFile.fromSingleUri(context, uri)?.name ?: return null
            val outputFile = File(directory, fileName)

            return runCatching {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    withContext(Dispatchers.IO) {
                        FileOutputStream(outputFile).use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                }
                Uri.fromFile(outputFile)
            }.getOrElse { exception ->
                exception.printStackTrace()
                null
            }
        }
    }
}