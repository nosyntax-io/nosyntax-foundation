package io.nosyntax.foundation.core.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Connectivity private constructor() {
    private lateinit var connectivityManager: ConnectivityManager

    companion object {
        private var instance: Connectivity? = null

        fun getInstance(): Connectivity {
            if (instance == null) {
                synchronized(Connectivity::class.java) {
                    if (instance == null) {
                        instance = Connectivity()
                    }
                }
            }
            return instance!!
        }
    }

    fun initializeWithApplicationContext(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isOnline(): Boolean {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}