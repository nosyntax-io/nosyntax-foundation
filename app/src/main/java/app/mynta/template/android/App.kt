package app.mynta.template.android

import android.app.Application
import android.content.Intent
import android.net.Uri
import app.mynta.template.android.core.Constants
import app.mynta.template.android.core.service.OneSignalService
import app.mynta.template.android.core.utility.Connectivity
import app.mynta.template.android.core.utility.monetize.MonetizeController
import app.mynta.template.android.presentation.main.MainActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Connectivity.getInstance().initializeWithApplicationContext(context = this)

        OneSignalService(this)
            .initialize(appId = BuildConfig.ONE_SIGNAL_APP_ID)
            .handleNotification(onNotificationOpened = { notificationData ->
                val deeplink = notificationData.first
                when (notificationData.second) {
                    "APP_BROWSER" -> {
                        val intent = Intent(this, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
                            if (deeplink.isNotEmpty()) {
                                putExtra(Constants.DEEPLINK, deeplink)
                            }
                        }
                        startActivity(intent)
                    }
                    "EXTERNAL_BROWSER" -> {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(deeplink)).apply {
                            flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                    }
                }
            })

        MonetizeController.initialize(this)
    }
}