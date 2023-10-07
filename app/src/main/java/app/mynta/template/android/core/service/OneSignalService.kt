package app.mynta.template.android.core.service

import android.content.Context
import android.util.Log
import com.onesignal.OSNotification
import com.onesignal.OneSignal
import org.json.JSONException
import org.json.JSONObject

class OneSignalService(private val context: Context) {
    /**
     * Initialize OneSignal with the provided app ID and configure logging.
     *
     * @param appId The OneSignal app id.
     * @param logLevel The log level to set
     */
    fun initialize(appId: String, logLevel: OneSignal.LOG_LEVEL = OneSignal.LOG_LEVEL.NONE): OneSignalService {
        OneSignal.setLogLevel(logLevel, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(context)
        OneSignal.setAppId(appId)
        OneSignal.promptForPushNotifications()
        return this
    }

    fun handleNotification(onNotificationOpened: (Pair<String, String>) -> Unit): OneSignalService  {
        OneSignal.setNotificationOpenedHandler { result ->
            try {
                val notification = result.notification
                val additionalData = notification.additionalData

                val (deeplink, deeplinkType) = extractNotificationData(notification, additionalData)
                onNotificationOpened(Pair(deeplink, deeplinkType))
            } catch (e: JSONException) {
                Log.e("OneSignalNotifications", "Error parsing notification data: ${e.message}")
            }
        }
        return this
    }

    /**
     * Extract notification data.
     *
     * @param notification The OneSignal notification.
     * @param additionalData Additional data from the notification.
     * @return A pair containing the deeplink and deeplink type.
     */
    private fun extractNotificationData(notification: OSNotification, additionalData: JSONObject?): Pair<String, String> {
        var deeplink = ""
        var deeplinkType = "IN_APP_BROWSER"

        if (notification.launchURL != null) {
            deeplink = notification.launchURL
            deeplinkType = "IN_APP_BROWSER"
        }

        additionalData?.let {
            val customKeyDeeplink = it.optString("deeplink", "")
            if (customKeyDeeplink.isNotEmpty()) {
                val customKeyType = it.optString("deeplink_type", "IN_APP_BROWSER")
                deeplink = customKeyDeeplink
                deeplinkType = customKeyType
            }
        }

        return Pair(deeplink, deeplinkType)
    }
}