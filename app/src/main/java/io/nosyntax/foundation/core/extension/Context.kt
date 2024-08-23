package io.nosyntax.foundation.core.extension

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.MailTo
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import io.nosyntax.foundation.R

/**
 * Recursively finds the enclosing Activity from a given Context.
 *
 * This function traverses through the Context hierarchy to locate the
 * nearest Activity. If no Activity is found, it throws an IllegalStateException.
 *
 * @return The Activity associated with the provided Context.
 * @throws IllegalStateException if no Activity is found in the Context hierarchy.
 */
internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("No Activity found in the context hierarchy.")
}

/**
 * Handles different types of URIs by delegating to appropriate functions based on the URI scheme.
 *
 * The [url] parameter should be a URI in string format.
 * The function determines the scheme of the URI and invokes the corresponding function:
 * - "mailto" -> `openMailer`
 * - "tel" -> `openDialer`
 * - "sms" -> `openSMS`
 * - Any other scheme -> `openContent`
 *
 * @param url The URI in string format to be handled.
 */
fun Context.handleIntent(url: String) {
    val scheme = Uri.parse(url).scheme
    when (scheme) {
        "mailto" -> openMailer(url)
        "tel" -> openDialer(url)
        "sms" -> openSMS(url)
        else -> openContent(url)
    }
}

/**
 * Opens the content specified by the given URI.
 *
 * The [data] parameter can be a URI for various types of content or intents, such as web pages,
 * email drafts, phone numbers, locations, markets, or social media profiles.
 * If no app can handle the URI, the function will handle the failure.
 *
 * @param data The URI or intent data to be opened.
 */
fun Context.openContent(data: String) {
    try {
        Intent(Intent.ACTION_VIEW, Uri.parse(data)).also(::startActivity)
    } catch (exception: ActivityNotFoundException) {
        Toast.makeText(this, R.string.no_apps_handle, Toast.LENGTH_LONG).show()
    }
}

/**
 * Opens the default email client to compose an email based on a mailto URL.
 *
 * The [data] parameter should be a mailto URL that specifies recipients, subject, and body of the email.
 * Multiple recipients can be included in the URL, separated by commas. For example:
 * ```
 * mailto:person1@domain.example,person2@domain.example?subject=Hello&body=World
 * ```
 *
 * @param data The mailto URL with recipients, subject, and body for the email.
 */
fun Context.openMailer(data: String) {
    val mail = MailTo.parse(data)
    val recipients = mail.to?.split(",")?.toTypedArray() ?: emptyArray()
    val subject = mail.subject.orEmpty()
    val body = mail.body.orEmpty()

    openMailer(recipients, subject, body)
}

/**
 * Opens the default email client to compose an email.
 *
 * @param recipients An array of email addresses of the recipients.
 * @param subject The subject of the email (optional).
 * @param body The body of the email (optional).
 */
fun Context.openMailer(
    recipients: Array<String>,
    subject: String = "",
    body: String = ""
) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, recipients)
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, getString(R.string.no_apps_handle), Toast.LENGTH_LONG).show()
    }
}

/**
 * Opens the dialer to dial a phone number.
 *
 * The [data] parameter can be either a phone number (e.g., `1234567890`)
 * or a tel URI (e.g., `tel:1234567890`).
 *
 * @param data The phone number or tel URI.
 */
fun Context.openDialer(data: String) {
    val uriString = if (data.startsWith("tel:")) data else "tel:$data"
    try {
        Intent(Intent.ACTION_DIAL, Uri.parse(uriString)).also(::startActivity)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, getString(R.string.no_apps_handle), Toast.LENGTH_LONG).show()
    }
}

/**
 * Opens the default SMS app to compose a message.
 *
 * The [data] parameter can be either a phone number (e.g., `1234567890`)
 * or an sms URI (e.g., `sms:1234567890`).
 *
 * @param data The phone number or SMS URI.
 */
fun Context.openSMS(data: String) {
    val uriString = if (data.startsWith("sms:")) data else "sms:$data"
    try {
        Intent(Intent.ACTION_SENDTO, Uri.parse(uriString)).also(::startActivity)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, getString(R.string.no_apps_handle), Toast.LENGTH_LONG).show()
    }
}

/**
 * Opens the specified app's page in the Google Play Store.
 * If the Play Store is unavailable, it opens the page in a web browser.
 *
 * @param packageName The package name of the app to be opened.
 */
fun Context.openPlayStore(packageName: String) {
    val uri = Uri.parse("market://details?id=$packageName")
    val fallbackUri = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")

    Intent(Intent.ACTION_VIEW, uri).also { intent ->
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            intent.data = fallbackUri
            startActivity(intent)
        }
    }
}

/**
 * Opens the app's settings page in the device settings,
 * allowing the user to manage permissions, storage, and other app-specific settings.
 */
fun Context.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}