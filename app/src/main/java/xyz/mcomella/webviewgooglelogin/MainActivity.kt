package xyz.mcomella.webviewgooglelogin

import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_DPAD_DOWN
import android.view.KeyEvent.KEYCODE_DPAD_LEFT
import android.view.KeyEvent.KEYCODE_DPAD_UP
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.settings.apply {
            javaScriptEnabled = true

            // Override UA because Google disabled log in with default WebView UA:
            //   https://developers.googleblog.com/2016/08/modernizing-oauth-interactions-in-native-apps.html
            // Other people claim updating the UA fixes this problem: https://stackoverflow.com/a/50374466
            //
            // This UA is used by our product and provides the desktop Pinterest experience.
            // However, this behavior is reproducible in Focus and Firefox for Fire TV which use a
            // mobile user agent.
            userAgentString = "Mozilla/5.0 (Linux; Android 5.1.1) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Focus/1.1 Chrome/59.0.3017.125 Safari/537.36"

            // We don't support multi-window and google login on nytimes works without it. though desktop and Chrome use multi-window in this flow.
            // Setting up multi-window support doesn't seem to fix this issue either though.
            setSupportMultipleWindows(false)
            javaScriptCanOpenWindowsAutomatically = false

            // Enable everything that seems like it could break the google log in experience.
            databaseEnabled = true
            domStorageEnabled = true
            setAppCacheEnabled(true)
            setGeolocationEnabled(true)
            loadWithOverviewMode = true
        }

        CookieManager.getInstance().apply {
            setAcceptCookie(true)
            setAcceptThirdPartyCookies(webView, true)
        }

        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()

        // Load other urls with key events (on the emulator) or by swapping this url with others.
        webView.loadUrl("https://pinterest.com/")
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        when (event?.keyCode) {
            KEYCODE_DPAD_DOWN -> webView.loadUrl("https://pinterest.com") // google login does not work
            KEYCODE_DPAD_UP -> webView.loadUrl("https://nytimes.com") // google login works
            KEYCODE_DPAD_LEFT -> webView.loadUrl("https://google.com") // to check login state

            else -> super.dispatchKeyEvent(event)
        }

        return true
    }
}

