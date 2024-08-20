package io.nosyntax.foundation

import android.app.Application
import android.content.Intent
import android.net.Uri
import io.nosyntax.foundation.core.service.OneSignalService
import io.nosyntax.foundation.core.util.Connectivity
import io.nosyntax.foundation.core.util.monetize.MonetizeController
import io.nosyntax.foundation.presentation.MainActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Connectivity.getInstance().initialize(this)

        OneSignalService(this)
            .initialize(appId = BuildConfig.ONESIGNAL_APP_ID)
            .registerOnNotificationClick { deeplink ->
                val (destination, action) = deeplink

                val intent = when (action) {
                    "IN_APP_WEBVIEW" -> Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
                        putExtra("deeplink", deeplink)
                    }
                    "EXTERNAL_BROWSER" -> Intent(Intent.ACTION_VIEW, Uri.parse(destination)).apply {
                        flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    else -> null
                }
                intent?.let { startActivity(it) }
            }

        MonetizeController.initialize(this)
    }
}