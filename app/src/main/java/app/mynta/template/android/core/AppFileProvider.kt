package app.mynta.template.android.core

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class AppFileProvider: FileProvider() {
    companion object {
        private suspend fun writeToCachedFile(context: Context, uri: Uri): Uri? {
            val cacheDir = File(context.cacheDir, "file_uploads")
            if (!cacheDir.exists()) {
                cacheDir.mkdir()
            }

            val fileName = DocumentFile.fromSingleUri(context, uri)?.name
            val cachedFile = File(cacheDir, fileName ?: return null)

            return try {
                val inputStream = context.contentResolver.openInputStream(uri) ?: return null
                val outputStream = withContext(Dispatchers.IO) {
                    FileOutputStream(cachedFile)
                }

                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }

                Uri.fromFile(cachedFile)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        suspend fun writeUriToFile(context: Context, uri: Uri): Uri? {
            return writeToCachedFile(context, uri)
        }

        fun contentUriForFile(context: Context, fileUri: Uri): Uri? {
            return try {
                val cachedFile = File(fileUri.path ?: return null)
                val authority = "${context.packageName}.provider"
                val cachedUri = getUriForFile(context, authority, cachedFile)

                cachedUri
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}