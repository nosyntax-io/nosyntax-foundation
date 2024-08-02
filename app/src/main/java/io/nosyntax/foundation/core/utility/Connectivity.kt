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

    fun initialize(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isOnline(): Boolean {
        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}