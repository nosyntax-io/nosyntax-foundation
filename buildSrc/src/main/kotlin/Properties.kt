import java.io.File
import java.io.IOException
import java.util.Properties

class Properties() {
    fun load(file: File): Properties {
        val properties = Properties()
        try {
            if (file.exists()) properties.load(file.inputStream())
            else println("Warning: ${file.name} file not found.")
        } catch (exception: IOException) {
            println("Warning: Error loading ${file.name} file: ${exception.message}")
        }
        return properties
    }
}