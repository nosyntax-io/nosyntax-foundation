import java.io.File
import java.io.IOException
import java.util.Properties

class Properties {
    fun load(file: File): Properties? = if (file.exists()) {
        try {
            Properties().apply { load(file.inputStream()) }
        } catch (e: IOException) {
            println("Warning: Error loading ${file.name}: ${e.message}")
            null
        }
    } else {
        println("Warning: ${file.name} not found.")
        null
    }
}