package io.nosyntax.template.android.presentation.web.utility

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.WebChromeClient
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.core.net.toUri
import io.nosyntax.template.android.core.AppFileProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class FileChooserDelegate(val context: Context, val onReceiveResult: (results: Array<Uri>?) -> Unit): CoroutineScope {
    private val browseFilesDelegate = BrowseFilesDelegate(context)
    private val cameraCaptureDelegate = CameraCaptureDelegate(context)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + Job()

    fun onShowFileChooser(params: WebChromeClient.FileChooserParams, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>): Boolean {
        openChooser(params, launcher)
        return true
    }

    fun onActivityResult(intent: Intent?) {
        when (intent.containsFileResult()) {
            true -> browseFilesDelegate.handleResult(intent) { onReceiveResult(it) }
            else -> cameraCaptureDelegate.handleResult { onReceiveResult(it) }
        }
    }

    private fun openChooser(params: WebChromeClient.FileChooserParams, launcher : ManagedActivityResultLauncher<Intent, ActivityResult>) {
        val cameraIntent = cameraCaptureDelegate.buildIntent(params)
        val chooserIntent = browseFilesDelegate.buildIntent(params)
        val extraIntents = listOfNotNull(cameraIntent).toTypedArray()

        val intent = Intent(Intent.ACTION_CHOOSER).apply {
            putExtra(Intent.EXTRA_INTENT, chooserIntent)
            putExtra(Intent.EXTRA_TITLE, params.title)
            putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents)
        }
        launcher.launch(intent)
    }

    internal class CameraCaptureDelegate(val context: Context) {
        companion object {
            private var cameraImagePath: String? = null
        }

        fun buildIntent(params: WebChromeClient.FileChooserParams): Intent? {
            if (!params.allowsCameraCapture()) return null

            val file = createEmptyImageFile() ?: return null
            val uri = AppFileProvider.getUriForFile(context, file.toUri())

            cameraImagePath = file.absolutePath

            return Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
        }

        fun handleResult(onResult: (Array<Uri>?) -> Unit) {
            val results = buildCameraImageResult()

            onResult(results)
            cameraImagePath = null
        }

        private fun buildCameraImageResult(): Array<Uri>? {
            val filePath = cameraImagePath ?: return null
            val file = File(filePath)
            val uri = AppFileProvider.getUriForFile(context, file.toUri()) ?: return null

            return when (file.length()) {
                0L -> null
                else -> arrayOf(uri)
            }
        }

        private fun createEmptyImageFile(): File? {
            return try {
                val directory = File(context.cacheDir, "app_cache")
                if (!directory.exists()) {
                    directory.mkdir()
                }
                return File.createTempFile("Capture_", ".jpg", directory)
            } catch (e: IOException) {
                null
            }
        }

        private fun WebChromeClient.FileChooserParams.allowsCameraCapture(): Boolean {
            val accept = defaultAcceptType()
            val acceptsAny = accept == "*/*"
            val acceptsImages = accept == "image/*" || accept == "image/jpg"
            return isCaptureEnabled && (acceptsAny || acceptsImages)
        }

        private fun defaultAcceptType(): String {
            return "image/*"
        }
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
            return AppFileProvider.writeUriToFile(context, File(context.cacheDir, "app_cache"), uri)?.let {
                AppFileProvider.getUriForFile(context, it)
            }
        }
    }

    private fun Intent?.containsFileResult(): Boolean {
        if (this == null) {
            return false
        }

        val clipData = this.clipData
        val dataString = this.dataString

        return clipData != null || dataString != null
    }
}