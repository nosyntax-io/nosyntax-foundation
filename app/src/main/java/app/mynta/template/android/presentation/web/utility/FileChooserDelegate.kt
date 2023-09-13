package app.mynta.template.android.presentation.web.utility

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.WebChromeClient
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.coroutines.CoroutineContext

class FileChooserDelegate(val context: Context, val onReceiveResult: (results: Array<Uri>?) -> Unit): CoroutineScope {
    private val browseFilesDelegate = BrowseFilesDelegate(context)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + Job()

    fun onShowFileChooser(params: WebChromeClient.FileChooserParams, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>): Boolean {
        openChooser(params, launcher)
        return true
    }

    fun onActivityResult(data: Intent?) {
        browseFilesDelegate.handleResult(data) { results ->
            onReceiveResult(results)
        }
    }

    private fun openChooser(params: WebChromeClient.FileChooserParams, launcher : ManagedActivityResultLauncher<Intent, ActivityResult>) {
        val chooserIntent = browseFilesDelegate.buildIntent(params)

        val intent = Intent(Intent.ACTION_CHOOSER).apply {
            putExtra(Intent.EXTRA_INTENT, chooserIntent)
            putExtra(Intent.EXTRA_TITLE, params.title)
        }
        launcher.launch(intent)
    }

    internal class BrowseFilesDelegate(val context: Context) : CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = Dispatchers.IO + Job()

        fun buildIntent(params: WebChromeClient.FileChooserParams): Intent {
            return Intent(Intent.ACTION_GET_CONTENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                setDataAndType(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "*/*"
                )

                if (params.acceptTypes.size > 1) {
                    putExtra(Intent.EXTRA_MIME_TYPES, params.acceptTypes)
                }
            }
        }

        fun handleResult(intent: Intent?, onResult: (Array<Uri>?) -> Unit) {
            if (intent == null) {
                onResult(null)
                return
            }

            launch {
                val clipData = intent.clipData
                val dataString = intent.dataString
                val results = when {
                    clipData != null -> buildMultipleFilesResult(clipData)
                    dataString != null -> buildSingleFileResult(dataString)
                    else -> null
                }

                onResult(results)
            }
        }

        private suspend fun buildMultipleFilesResult(clipData: ClipData): Array<Uri>? {
            val uris = mutableListOf<Uri>()

            for (i in 0 until clipData.itemCount) {
                uris.add(clipData.getItemAt(i).uri)
            }

            return buildResult(uris)
        }

        private suspend fun buildSingleFileResult(dataString: String): Array<Uri>? {
            val uri = Uri.parse(dataString)
            return buildResult(listOf(uri))
        }

        private suspend fun buildResult(uris: List<Uri>): Array<Uri>? {
            val results = uris.mapNotNull {
                writeToCachedFile(it)
            }

            return when (results.isEmpty()) {
                true -> null
                else -> results.toTypedArray()
            }
        }

        private suspend fun writeToCachedFile(uri: Uri): Uri? {
            val cacheDir = File(context.cacheDir, "file_uploads")
            if (!cacheDir.exists()) {
                cacheDir.mkdir()
            }

            val fileName = DocumentFile.fromSingleUri(context, uri)?.name
            val cachedFile = File(cacheDir, fileName!!)

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
    }
}