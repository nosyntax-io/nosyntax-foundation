package app.mynta.template.android.core.utility

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import app.mynta.template.android.R

object Intents {
    fun Context.openUrl(url: String) {
        try {
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).also { intent ->
                startActivity(intent)
            }
        } catch (exception: ActivityNotFoundException) {
            Toast.makeText(this, this.getString(R.string.no_apps_handle), Toast.LENGTH_LONG).show()
        }
    }

    fun Context.openPlayStore(packageName: String) {
        try {
            Intent(Intent.ACTION_VIEW).also { intent ->
                intent.data = Uri.parse("market://details?id=$packageName")
                startActivity(intent)
            }
        } catch (e : ActivityNotFoundException) {
            Intent(Intent.ACTION_VIEW).also { intent ->
                intent.data = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                startActivity(intent)
            }
        }
    }

    fun Context.openEmail(recipient: String, subject: String = "", body: String = "") {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.no_apps_handle), Toast.LENGTH_LONG).show()
        }
    }

    fun Context.openDial(url: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    fun Context.openSMS(url: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(url))
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }


}