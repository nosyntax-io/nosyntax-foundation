package app.mynta.template.android.core.utility

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import app.mynta.template.android.domain.file_operation.Downloader

class Download(context: Context): Downloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(fileName: String, url: String, mimeType: String): Long {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(fileName)
            .setDescription("Downloading file...")
            .setMimeType(mimeType)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        return downloadManager.enqueue(request)
    }
}