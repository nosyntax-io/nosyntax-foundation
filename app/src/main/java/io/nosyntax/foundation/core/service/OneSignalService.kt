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

data class DeeplinkData(
    val destination: String,
    val action: String
)

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
     * Set up a click listener for notifications.
     *
     * @param onNotificationOpened A callback invoked when a notification is clicked.
     * @return The current instance of OneSignalService.
     */
    fun registerOnNotificationClick(onNotificationOpened: (DeeplinkData) -> Unit): OneSignalService {
        OneSignal.Notifications.addClickListener(object: INotificationClickListener {
            override fun onClick(event: INotificationClickEvent) {
                val notification = event.notification
                val additionalData = notification.additionalData

                val deeplinkData = extractDeeplinkData(notification, additionalData)
                onNotificationOpened(deeplinkData)
            }
        })
        return this
    }

    /**
     * Extract deeplink data from the notification.
     *
     * @param notification The OneSignal notification object.
     * @param additionalData JSON object containing additional notification data.
     * @return A DeeplinkData object containing the extracted destination and type.
     */
    private fun extractDeeplinkData(notification: INotification, additionalData: JSONObject?): DeeplinkData {
        var destination = ""
        var action = ""

        if (!notification.launchURL.isNullOrEmpty()) {
            destination = notification.launchURL!!
            action = "IN_APP_WEBVIEW"
        }

        additionalData?.let {
            destination = it.optString("deeplink_destination", destination)
            action = it.optString("deeplink_action", action)
        }

        return DeeplinkData(destination, action)
    }
}