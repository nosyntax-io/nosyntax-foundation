package io.nosyntax.foundation.core.utility

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.MailTo
import android.net.Uri
import android.widget.Toast
import io.nosyntax.foundation.R

object Intents {
    fun Context.handleUrlAction(url: String) {
        when {
            url.startsWith("mailto:") -> openEmail(url)
            url.startsWith("tel:") -> openDial(url)
            url.startsWith("sms:") -> openSMS(url)
            url.startsWith("market://") -> openPlayStore(packageName)
        }
    }

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

    fun Context.openEmail(email: String, subject: String = "", body: String = "") {
        try {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email")).apply {
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    fun Context.openEmail(url: String) {
        val mail = MailTo.parse(url)
        val recipient = mail.to
        val subject = mail.subject ?: ""
        val body = mail.body ?: ""

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