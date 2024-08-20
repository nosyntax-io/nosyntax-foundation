package io.nosyntax.foundation.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Connectivity private constructor() {
    private lateinit var connectivityManager: ConnectivityManager

    companion object {
        @Volatile
        private var instance: Connectivity? = null

        fun getInstance(): Connectivity =
            instance ?: synchronized(this) {
                instance ?: Connectivity().also { instance = it }
            }
    }

    fun initialize(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isOnline(): Boolean {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasAnyTransport() ?: false
    }

    private fun NetworkCapabilities.hasAnyTransport(): Boolean {
        return listOf(
            NetworkCapabilities.TRANSPORT_CELLULAR,
            NetworkCapabilities.TRANSPORT_WIFI,
            NetworkCapabilities.TRANSPORT_ETHERNET
        ).any { hasTransport(it) }
    }
}