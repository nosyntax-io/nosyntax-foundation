package io.nosyntax.foundation.presentation.screen.web.util

/**
 * Represents different types of JavaScript dialogs.
 */
sealed class JsDialog {
    /**
     * Represents an alert dialog.
     * @param message The message to be shown in the alert.
     */
    data class Alert(val message: String) : JsDialog()

    /**
     * Represents a confirm dialog.
     * @param message The message to be shown in the confirm dialog.
     */
    data class Confirm(val message: String) : JsDialog()

    /**
     * Represents a prompt dialog.
     * @param message The message to be shown in the prompt dialog.
     * @param defaultValue The default value in the prompt input field.
     */
    data class Prompt(val message: String, val defaultValue: String = "") : JsDialog()
}