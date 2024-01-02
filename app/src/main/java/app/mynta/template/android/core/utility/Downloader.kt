package app.mynta.template.android.core.utility

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import app.mynta.template.android.domain.file_operation.FileDownloader

class Downloader(context: Context): FileDownloader {
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
            .setDescription("Downloading file...")
            .setMimeType(mimeType)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        userAgent?.let {
            request.addRequestHeader("User-Agent", it)
        }

        return downloadManager.enqueue(request)
    }
}