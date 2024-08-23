@file:Suppress("DEPRECATION")

package io.nosyntax.foundation.core.util

import android.app.Activity
import android.content.Context
import android.os.Build
import com.google.gson.Gson
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utils {
    /**
     * Retrieves the current year as a string.
     */
    fun getCurrentYear(): String =
        SimpleDateFormat("yyyy", Locale.getDefault()).format(Calendar.getInstance().time)

    /**
     * Retrieves a serializable object from the intent.
     *
     * @param activity The activity containing the intent.
     * @param name The name of the extra to retrieve.
     * @param clazz The class of the serializable object.
     * @return The serializable object, or null if not found.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Serializable?> getSerializable(
        activity: Activity,
        name: String,
        clazz: Class<T>
    ): T? {
        return runCatching {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                activity.intent.getSerializableExtra(name, clazz)
            } else {
                activity.intent.getSerializableExtra(name) as? T
            }
        }.getOrNull()
    }

    /**
     * Reads a JSON file from assets and parses it into a DTO.
     *
     * @param context The context used to access assets.
     * @param path The path to the JSON file within the assets directory.
     * @param clazz The class of the DTO to parse the JSON into.
     * @return The parsed DTO, or null if an error occurs.
     */
    fun <T> getDtoFromJson(
        context: Context,
        path: String,
        clazz: Class<T>
    ): T? {
        return runCatching {
            context.assets.open(path).use { inputStream ->
                val json = inputStream.bufferedReader().use { it.readText() }
                Gson().fromJson(json, clazz)
            }
        }.onFailure {
            it.printStackTrace()
        }.getOrNull()
    }
}