package app.mynta.template.android.domain.file_operation

interface Downloader {
    fun downloadFile(fileName: String, url: String, userAgent: String?, mimeType: String): Long
}