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

                val (notificationUrl, notificationUrlOpenType) = extractNotificationData(notification, additionalData)
                onNotificationOpened(Pair(notificationUrl, notificationUrlOpenType))

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
     * @return A pair containing the URL and open type.
     */
    private fun extractNotificationData(notification: OSNotification, additionalData: JSONObject?): Pair<String, String> {
        var notificationUrl = ""
        var notificationUrlOpenType = "INSIDE"

        if (notification.launchURL != null) {
            notificationUrl = notification.launchURL
            notificationUrlOpenType = "INSIDE"
        }

        additionalData?.let {
            val customKeyUrl = it.optString("url", "")
            if (customKeyUrl.isNotEmpty()) {
                val customKeyType = it.optString("url_type", "INSIDE")
                notificationUrl = customKeyUrl
                notificationUrlOpenType = customKeyType
            }
        }

        return Pair(notificationUrl, notificationUrlOpenType)
    }
}