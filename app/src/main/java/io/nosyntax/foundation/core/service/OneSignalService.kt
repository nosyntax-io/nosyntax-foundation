package io.nosyntax.foundation.core.service

import android.content.Context
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import com.onesignal.notifications.INotification
import com.onesignal.notifications.INotificationClickEvent
import com.onesignal.notifications.INotificationClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class OneSignalService(private val context: Context) {
    /**
     * Initialize OneSignal with the provided app ID and logging level.
     *
     * @param appId The OneSignal application ID.
     * @param logLevel The logging level for debugging.
     * @return The current instance of OneSignalService.
     */
    fun initialize(appId: String, logLevel: LogLevel = LogLevel.NONE): OneSignalService {
        OneSignal.Debug.logLevel = logLevel
        OneSignal.initWithContext(context, appId)
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(false)
        }
        return this
    }

    /**
     * Sets up a click listener for notifications.
     *
     * @param onNotificationOpened A callback invoked when a notification is clicked.
     * @return The current instance of OneSignalService.
     */
    fun handleNotification(onNotificationOpened: (Pair<String, String>) -> Unit): OneSignalService {
        OneSignal.Notifications.addClickListener(object: INotificationClickListener {
            override fun onClick(event: INotificationClickEvent) {
                val notification = event.notification
                val additionalData = notification.additionalData

                val (deeplink, deeplinkType) = extractNotificationData(notification, additionalData)
                onNotificationOpened(Pair(deeplink, deeplinkType))
            }
        })
        return this
    }

    /**
     * Extracts deeplink and type from the notification.
     *
     * @param notification The OneSignal notification object.
     * @param additionalData JSON object containing additional notification data.
     * @return A pair where the first element is the deeplink and the second element is the deeplink type.
     */
    private fun extractNotificationData(notification: INotification, additionalData: JSONObject?): Pair<String, String> {
        var deeplink = ""
        var deeplinkType = ""

        if (notification.launchURL != null) {
            deeplink = notification.launchURL ?: ""
            deeplinkType = "APP_BROWSER"
        }

        additionalData?.let {
            val customKeyDeeplink = it.optString("deeplink", "")
            if (customKeyDeeplink.isNotEmpty()) {
                val customKeyType = it.optString("deeplink_type", "")
                deeplink = customKeyDeeplink
                deeplinkType = customKeyType
            }
        }

        return Pair(deeplink, deeplinkType)
    }
}