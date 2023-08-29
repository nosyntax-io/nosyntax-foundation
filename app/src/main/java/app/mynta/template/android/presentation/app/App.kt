package app.mynta.template.android.presentation.app

import android.app.Application
import app.mynta.template.android.R
import app.mynta.template.android.core.utility.Connectivity
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        @get:Synchronized
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        Connectivity.getInstance().initializeWithApplicationContext(context = this)

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(getString(R.string.app_name))
        OneSignal.promptForPushNotifications()
    }
}