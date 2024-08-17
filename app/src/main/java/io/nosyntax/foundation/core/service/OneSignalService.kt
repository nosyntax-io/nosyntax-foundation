package io.nosyntax.foundation.core.service

import android.content.Context
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import com.onesignal.notifications.INotification
import com.onesignal.notifications.INotificationClickEvent
import com.onesignal.notifications.INotificationClickListener
import io.nosyntax.foundation.domain.model.Deeplink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OneSignalService(private val context: Context) {
    /**
     * Initialize OneSignal with the provided app ID and logging level.
     *
     * @param appId The OneSignal application ID.
     * @param logLevel The logging level for debugging.
     * @return The current instance of OneSignalService.
     */
    fun initialize(appId: String, logLevel: LogLevel = LogLevel.DEBUG): OneSignalService {
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
    fun registerOnNotificationClick(onNotificationOpened: (Deeplink) -> Unit): OneSignalService {
        OneSignal.Notifications.addClickListener(object : INotificationClickListener {
            override fun onClick(event: INotificationClickEvent) {
                val deeplink = extractDeeplink(event.notification)
                onNotificationOpened(deeplink)
            }
        })
        return this
    }

    /**
     * Extract deeplink data from the notification.
     *
     * @param notification The OneSignal notification object.
     * @return A Deeplink object containing the extracted destination and action.
     */
    private fun extractDeeplink(notification: INotification): Deeplink {
        var destination = ""
        var action = ""

        if (!notification.launchURL.isNullOrEmpty()) {
            destination = notification.launchURL!!
            action = "IN_APP_WEBVIEW"
        }

        notification.additionalData?.let {
            destination = it.optString("deeplink_destination", destination)
            action = it.optString("deeplink_action", action)
        }

        return Deeplink(destination, action)
    }
}