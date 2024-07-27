package tasks

import AppConfig
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

open class SetPermissionsTask : DefaultTask() {
    @Input
    lateinit var config: AppConfig

    @TaskAction
    fun setPermissions() {
        val permissions = config.getPermissions()
        val manifestFile = project.file("src/main/AndroidManifest.xml")

        if (!manifestFile.exists() || !manifestFile.canRead() || !manifestFile.canWrite()) {
            throw IllegalStateException("Manifest file is not accessible")
        }

        val permissionDeclarations = permissions.joinToString("\n") { "    <uses-permission android:name=\"$it\" />" }
        val manifestContent = manifestFile.readText()
            .replace("""<uses-permission[^>]*>\s*""".toRegex(), "")
            .replace("""(\r?\n\s*)+<application""".toRegex(), "\n\n<application")
            .replace("<application", "$permissionDeclarations\n\n    <application")

        manifestFile.writeText(manifestContent)
    }
}