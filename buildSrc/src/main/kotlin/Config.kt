import org.yaml.snakeyaml.Yaml
import java.nio.file.Files
import java.nio.file.Path

class AppConfig(path: Path) {
    val config: Map<String, Any>

    init {
        val configContent = Files.readString(path)
        config = Yaml().load(configContent)
    }

    inline fun <reified T> get(path: String): T {
        val value = path.split(".").fold(config as Any?) { current, key ->
            (current as? Map<*, *>)?.get(key)
        }
        return (value as? T) ?: when (T::class) {
            String::class -> "" as T
            Int::class -> 0 as T
            Boolean::class -> false as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
}