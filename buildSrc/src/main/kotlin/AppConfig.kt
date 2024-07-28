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

    fun getBuildConfigFields(): Map<String, String> {
        val buildConfigKeys = mapOf(
            "SERVER_AUTH_TOKEN" to "server.auth_token",
            "SERVER_ACCESS_TOKEN" to "server.access_token",
            "APP_REMOTE_CONFIG" to "app.remote_config",
            "ONESIGNAL_APP_ID" to "integrations.onesignal.app_id",
            "ADMOB_BANNER_ID" to "integrations.admob.banner_id",
            "ADMOB_INTERSTITIAL_ID" to "integrations.admob.interstitial_id"
        )

        return buildConfigKeys.mapValues { get<String>(it.value) }
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