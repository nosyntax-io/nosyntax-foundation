import org.yaml.snakeyaml.Yaml
import java.nio.file.Files
import java.nio.file.Path

class AppConfig(path: Path) {
    val config: Map<String, Any> = Yaml().load(Files.readString(path))

    inline fun <reified T> get(path: String): T {
        val value = path.split(".").fold(config as Any?) { current, key ->
            (current as? Map<*, *>)?.get(key)
        }
        return (value as? T) ?: when (T::class) {
            String::class -> "" as T
            Int::class -> 0 as T
            Double::class -> 0 as T
            Boolean::class -> false as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    fun getBuildConfigFields(): Map<String, Any> {
        return mapOf(
            "DYNAMIC_CONFIG_ENABLED" to "dynamic_config.enabled",
            "DYNAMIC_CONFIG_TOKEN" to "dynamic_config.access_token",
            "ONESIGNAL_ENABLED" to "integrations.onesignal.enabled",
            "ONESIGNAL_APP_ID" to "integrations.onesignal.app_id"
        ).mapValues { (_, path) -> get<Any>(path) }
    }

    fun getPermissions(): List<String> {
        val permissionMapping = mapOf(
            "internet" to "android.permission.INTERNET",
            "network_state" to "android.permission.ACCESS_NETWORK_STATE",
            "storage" to "android.permission.WRITE_EXTERNAL_STORAGE",
            "geolocation" to "android.permission.ACCESS_COARSE_LOCATION",
            "camera" to "android.permission.CAMERA",
            "contacts" to "android.permission.READ_CONTACTS"
        )

        val permissionsList = (config["permissions"] as? List<*> ?: emptyList<String>()).mapNotNull {
            permissionMapping[it as? String ?: ""]
        }
        return permissionsList
    }
}