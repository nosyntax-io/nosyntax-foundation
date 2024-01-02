package app.mynta.template.android.domain.file_operation

interface Downloader {
    fun downloadFile(fileName: String, url: String, mimeType: String): Long
}