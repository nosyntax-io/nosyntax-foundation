package io.nosyntax.foundation.domain.file_operation

interface FileDownloader {
    fun downloadFile(fileName: String, url: String, userAgent: String?, mimeType: String): Long
}