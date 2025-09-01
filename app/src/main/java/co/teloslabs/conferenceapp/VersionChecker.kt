package co.teloslabs.conferenceapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AlertDialog

class VersionChecker(private val activity: Activity) {

    companion object {
        private const val MINIMUM_REQUIRED_VERSION_CODE = 6
        private const val UPDATE_MESSAGE = "A new version of the app has been released with important improvements. Please update to continue enjoying the best experience."
        private const val PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=co.teloslabs.conferenceapp"
    }

    fun checkForUpdates() {

        val currentVersionCode = getCurrentVersionCode()

        if (currentVersionCode < MINIMUM_REQUIRED_VERSION_CODE) {
            showForceUpdateDialog()
        }
    }

    private fun getCurrentVersionCode(): Int {
        return try {
            val packageInfo = activity.packageManager.getPackageInfo(activity.packageName, 0)
            packageInfo.versionCode
        } catch (e: Exception) {
            0
        }
    }

    private fun showForceUpdateDialog() {
        AlertDialog.Builder(activity)
            .setTitle("Update Required")
            .setMessage(UPDATE_MESSAGE)
            .setCancelable(false)
            .setPositiveButton("Update") { _, _ ->
                redirectToPlayStore()
            }
            .setNegativeButton("Exit") { _, _ ->
                activity.finish()
            }
            .show()
    }

        private fun redirectToPlayStore() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${activity.packageName}"))
            activity.startActivity(intent)
        } catch (e: Exception) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_URL))
            activity.startActivity(intent)
        }

        activity.finish()
    }
}
