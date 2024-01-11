package io.nosyntax.foundation.core.utility

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import io.nosyntax.foundation.R
import io.nosyntax.foundation.domain.file_operation.FileDownloader

class Downloader(private val context: Context): FileDownloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    init {
        requireNotNull(downloadManager) { "DownloadManager not available" }
    }

    override fun downloadFile(fileName: String, url: String, userAgent: String?, mimeType: String): Long {
        require(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            "External storage not available"
        }

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(fileName)
            .setDescription(context.getString(R.string.file_is_downloading))
            .setMimeType(mimeType)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        userAgent?.let {
            request.addRequestHeader("User-Agent", it)
        }

        return downloadManager.enqueue(request)
    }
}