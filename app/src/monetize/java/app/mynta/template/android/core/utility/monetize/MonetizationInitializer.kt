package app.mynta.template.android.core.utility.monetize

import android.content.Context
import com.google.android.gms.ads.MobileAds

object MonetizationInitializer {
    fun initialize(context: Context) {
        MobileAds.initialize(context)
    }
}