package io.nosyntax.foundation.core.utility

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import com.google.gson.Gson
import java.io.IOException
import java.io.Serializable
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Utilities {
    fun getCurrentYear(): String {
        val calendar = Calendar.getInstance().time
        return SimpleDateFormat("yyyy", Locale.getDefault()).format(calendar)
    }

    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    fun <T : Serializable?> getSerializable(activity: Activity, name: String, clazz: Class<T>): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            activity.intent.getSerializableExtra(name, clazz)
        } else {
            activity.intent.getSerializableExtra(name) as T
        }
    }

    fun <T> getDtoFromJson(context: Context, path: String, clazz: Class<T>): T? {
        val json: String
        try {
            context.assets.open(path).use { inputStream ->
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                json = String(buffer, StandardCharsets.UTF_8)
                return Gson().fromJson(json, clazz)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return null
    }
}