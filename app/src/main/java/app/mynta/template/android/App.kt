package app.mynta.template.android

import android.app.Application
import android.util.Log
import app.mynta.template.android.core.service.OneSignalService
import app.mynta.template.android.core.utility.Connectivity
import app.mynta.template.android.core.utility.monetize.MonetizeController
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Connectivity.getInstance().initializeWithApplicationContext(context = this)

        OneSignalService(this)
            .initialize(appId = BuildConfig.ONE_SIGNAL_APP_ID)
            .handleNotification(onNotificationOpened = { notificationData ->
                Log.d("notificationData", "URL: ${notificationData.first}")
                Log.d("notificationData", "Open Type: ${notificationData.second}")
            })

        MonetizeController.initialize(this)
    }
}