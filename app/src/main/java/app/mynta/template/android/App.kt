package app.mynta.template.android

import android.app.Application
import app.mynta.template.android.core.utility.Connectivity
import app.mynta.template.android.core.utility.monetize.MonetizationInitializer
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Connectivity.getInstance().initializeWithApplicationContext(context = this)

        initializeOneSignal()

        MonetizationInitializer.initialize(this)
    }

    private fun initializeOneSignal() {
        // OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(BuildConfig.ONE_SIGNAL_APP_ID)
        OneSignal.promptForPushNotifications()
    }
}