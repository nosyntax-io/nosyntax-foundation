package io.nosyntax.template.android.domain.file_operation

interface FileDownloader {
    fun downloadFile(fileName: String, url: String, userAgent: String?, mimeType: String): Long
}