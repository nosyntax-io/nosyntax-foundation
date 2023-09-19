package app.mynta.template.android

import android.app.Application
import app.mynta.template.android.core.utility.Connectivity
import com.google.android.gms.ads.MobileAds
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
        OneSignal.setAppId(BuildConfig.ONE_SIGNAL_APP_ID)
        OneSignal.promptForPushNotifications()

        MobileAds.initialize(this)
    }
}