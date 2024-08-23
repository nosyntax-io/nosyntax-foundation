package io.nosyntax.foundation.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Connectivity private constructor() {
    private lateinit var connectivityManager: ConnectivityManager

    companion object {
        @Volatile
        private var instance: Connectivity? = null

        /**
         * Retrieves the singleton instance of the Connectivity class.
         * Uses double-checked locking to ensure thread-safe initialization.
         */
        fun getInstance(): Connectivity =
            instance ?: synchronized(this) {
                instance ?: Connectivity().also { instance = it }
            }
    }

    /**
     * Initializes the ConnectivityManager using the application context.
     *
     * @param context The application context.
     */
    fun initialize(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    /**
     * Checks if the device is currently online.
     *
     * @return True if the device is connected to a network, false otherwise.
     */
    fun isOnline(): Boolean {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasAnyTransport() ?: false
    }

    /**
     * Extension function to check if the network has any available transport (cellular, WiFi, ethernet).
     *
     * @return True if any of the specified transports are available, false otherwise.
     */
    private fun NetworkCapabilities.hasAnyTransport(): Boolean {
        return listOf(
            NetworkCapabilities.TRANSPORT_CELLULAR,
            NetworkCapabilities.TRANSPORT_WIFI,
            NetworkCapabilities.TRANSPORT_ETHERNET
        ).any { hasTransport(it) }
    }
}