package co.teloslabs.conferenceapp

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.ServiceWorkerClient
import android.webkit.ServiceWorkerController
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import dev.hotwire.core.config.Hotwire
import dev.hotwire.core.turbo.config.PathConfiguration
import dev.hotwire.navigation.activities.HotwireActivity
import dev.hotwire.navigation.navigator.NavigatorConfiguration
import java.io.ByteArrayInputStream

const val baseURL = "https://app.railsworld.com"

class MainActivity : HotwireActivity() {
    private lateinit var versionChecker: VersionChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        versionChecker = VersionChecker(this)

        versionChecker.checkForUpdates()
        val navHost = findViewById<View>(R.id.main_nav_host)
        ViewCompat.setOnApplyWindowInsetsListener(navHost) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                left = systemBars.left,
                right = systemBars.right,
                bottom = systemBars.bottom
            )
            insets
        }
        Hotwire.loadPathConfiguration(
            context = this,
            location = PathConfiguration.Location(
                assetFilePath = "configuration.json",
                remoteFileUrl = "$baseURL/configurations/android.json"
            )
        )

        ServiceWorkerController.getInstance().setServiceWorkerClient(object : ServiceWorkerClient() {
            override fun shouldInterceptRequest(request: WebResourceRequest): WebResourceResponse? {
                val uri = request.url
                val targetHost = try { Uri.parse(baseURL).host } catch (_: Throwable) { null }
                if (uri.host == targetHost && uri.path?.endsWith("/service-worker.js") == true) {
                    return WebResourceResponse(
                        "text/plain",
                        "UTF-8",
                        404,
                        "Not Found",
                        emptyMap(),
                        ByteArrayInputStream(ByteArray(0))
                    )
                }
                return null
            }
        })
    }

    override fun navigatorConfigurations() = listOf(
        NavigatorConfiguration(
            name = "main",
            startLocation = baseURL,
            navigatorHostId = R.id.main_nav_host
        )
    )
}