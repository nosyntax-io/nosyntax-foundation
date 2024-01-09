package io.nosyntax.template.android.core.utility.monetize

import android.content.Context
import com.google.android.gms.ads.MobileAds

object MonetizeController {
    fun initialize(context: Context) {
        MobileAds.initialize(context)
    }
}