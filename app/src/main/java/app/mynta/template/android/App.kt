package app.mynta.template.android

import android.app.Application
import android.content.Intent
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
                val notificationUrl = notificationData.first

                val intent = Intent(this.applicationContext, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
                }
                if (notificationUrl.isNotEmpty()) {
                    intent.putExtra(Constants.DEEPLINK, notificationUrl)
                }
                startActivity(intent)
            })

        MonetizeController.initialize(this)
    }
}