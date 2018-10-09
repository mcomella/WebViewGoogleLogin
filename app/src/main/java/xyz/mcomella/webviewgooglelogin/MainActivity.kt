package xyz.mcomella.webviewgooglelogin

import android.app.PendingIntent.getActivity
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.os.Build
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.ClientCertRequest
import android.webkit.ConsoleMessage
import android.webkit.GeolocationPermissions
import android.webkit.HttpAuthHandler
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.PermissionRequest
import android.webkit.RenderProcessGoneDetail
import android.webkit.SafeBrowsingResponse
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.settings.apply {
            setJavaScriptEnabled(true)
            setSupportMultipleWindows(false)
            setJavaScriptCanOpenWindowsAutomatically(false)
            setUserAgentString("Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36")
            setGeolocationEnabled(true)
            setUseWideViewPort(true)
            setLoadWithOverviewMode(true)
            setAllowContentAccess(true)
            setDatabaseEnabled(true)
            setLoadsImagesAutomatically(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW)
            }
        }
        webView.webChromeClient = MyWebChromeClient()
        webView.webViewClient = WVCLient()
        enableHTML5AppCache(webView)

        webView.loadUrl("https://kotaku.com/")
    }

    fun enableHTML5AppCache(mWebView: WebView) {

        mWebView.getSettings().setDomStorageEnabled(true);

        // Set cache size to 8 mb by default. should be more than enough
        mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);

        // This next one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line
        mWebView.getSettings().setAppCachePath("/data/data/" + this.getPackageName() + "/cache");
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);

        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    }
}

class WVCLient : WebViewClient() {
}

class MyWebChromeClient : WebChromeClient() {
}
