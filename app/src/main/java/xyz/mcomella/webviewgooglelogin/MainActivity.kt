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
    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        Log.d("lol", "onPageFinished $url")
    }

    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
        return super.shouldInterceptRequest(view, request).also {
            Log.d("lol", "shouldInterceptRequest ${it?.statusCode}")
        }
    }

    override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
        Log.d("lol", "shouldOverrideKeyEvent")
        return super.shouldOverrideKeyEvent(view, event)
    }

    override fun onSafeBrowsingHit(view: WebView?, request: WebResourceRequest?, threatType: Int, callback: SafeBrowsingResponse?) {
        super.onSafeBrowsingHit(view, request, threatType, callback)
        Log.d("lol", "onSafeBrowsingHit")
    }

    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
        super.doUpdateVisitedHistory(view, url, isReload)
        Log.d("lol", "doUpdateVisitedHistory")
    }

    override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        Log.d("lol", "onReceivedError")
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        Log.d("lol", "onReceivedError")
    }

    override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
        Log.d("lol", "onRenderProcessGone")
        return super.onRenderProcessGone(view, detail)
    }

    override fun onReceivedLoginRequest(view: WebView?, realm: String?, account: String?, args: String?) {
        super.onReceivedLoginRequest(view, realm, account, args)
        Log.d("lol", "onReceivedLoginRequest")
    }

    override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
        super.onReceivedHttpError(view, request, errorResponse)
        Log.d("lol", "onReceivedHttpError")
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Log.d("lol", "onPageStarted $url")
    }

    override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
        super.onScaleChanged(view, oldScale, newScale)
        Log.d("lol", "onScaleChanged")
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        Log.d("lol", "shouldOverrideUrlLoading")
        return super.shouldOverrideUrlLoading(view, url)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        Log.d("lol", "shouldOverrideUrlLoading")
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageCommitVisible(view: WebView?, url: String?) {
        super.onPageCommitVisible(view, url)
        Log.d("lol", "onPageCommitVisible")
    }

    override fun onUnhandledKeyEvent(view: WebView?, event: KeyEvent?) {
        super.onUnhandledKeyEvent(view, event)
//        Log.d("lol", "onUnhandledKeyEvent")
    }

    override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest?) {
        super.onReceivedClientCertRequest(view, request)
        Log.d("lol", "onReceivedClientCertRequest")
    }

    override fun onReceivedHttpAuthRequest(view: WebView?, handler: HttpAuthHandler?, host: String?, realm: String?) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm)
        Log.d("lol", "onReceivedHttpAuthRequest")
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
        Log.d("lol", "onReceivedSslError")
    }

    override fun onTooManyRedirects(view: WebView?, cancelMsg: Message?, continueMsg: Message?) {
        super.onTooManyRedirects(view, cancelMsg, continueMsg)
        Log.d("lol", "onTooManyRedirects")
    }

    override fun onFormResubmission(view: WebView?, dontResend: Message?, resend: Message?) {
        super.onFormResubmission(view, dontResend, resend)
        Log.d("lol", "onFormResubmission")
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
        Log.d("lol", "onLoadResource $url")
    }
}

class MyWebChromeClient : WebChromeClient() {
    override fun onRequestFocus(view: WebView?) {
        super.onRequestFocus(view)
        Log.d("lol", "onRequestFocus")
    }

    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        Log.d("lol", "onJsAlert")
        return super.onJsAlert(view, url, message, result)
    }

    override fun onJsPrompt(view: WebView?, url: String?, message: String?, defaultValue: String?, result: JsPromptResult?): Boolean {
        Log.d("lol", "onJsPrompt")
        return super.onJsPrompt(view, url, message, defaultValue, result)
    }

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        super.onShowCustomView(view, callback)
        Log.d("lol", "onShowCustomView")
    }

    override fun onShowCustomView(view: View?, requestedOrientation: Int, callback: CustomViewCallback?) {
        super.onShowCustomView(view, requestedOrientation, callback)
        Log.d("lol", "onShowCustomView")
    }

    override fun onGeolocationPermissionsShowPrompt(origin: String?, callback: GeolocationPermissions.Callback?) {
        super.onGeolocationPermissionsShowPrompt(origin, callback)
        Log.d("lol", "onGeolocationPermissionsShowPrompt")
    }

    override fun onPermissionRequest(request: PermissionRequest?) {
        super.onPermissionRequest(request)
        Log.d("lol", "onPermissionRequest")
    }

    override fun onConsoleMessage(message: String?, lineNumber: Int, sourceID: String?) {
        super.onConsoleMessage(message, lineNumber, sourceID)
        Log.d("lol", "onConsoleMessage")
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        Log.d("lol", "onConsoleMessage")
        return super.onConsoleMessage(consoleMessage)
    }

    override fun onPermissionRequestCanceled(request: PermissionRequest?) {
        super.onPermissionRequestCanceled(request)
        Log.d("lol", "onPermissionRequestCanceled")
    }

    override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
        Log.d("lol", "onShowFileChooser")
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
    }

    override fun onReceivedTouchIconUrl(view: WebView?, url: String?, precomposed: Boolean) {
        super.onReceivedTouchIconUrl(view, url, precomposed)
        Log.d("lol", "onReceivedTouchIconUrl")
    }

    override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
        super.onReceivedIcon(view, icon)
        Log.d("lol", "onReceivedIcon")
    }

    override fun onExceededDatabaseQuota(url: String?, databaseIdentifier: String?, quota: Long, estimatedDatabaseSize: Long, totalQuota: Long, quotaUpdater: WebStorage.QuotaUpdater?) {
        super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater)
        Log.d("lol", "onExceededDatabaseQuota")
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        Log.d("lol", "onReceivedTitle")
    }

    override fun onReachedMaxAppCacheSize(requiredStorage: Long, quota: Long, quotaUpdater: WebStorage.QuotaUpdater?) {
        super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater)
        Log.d("lol", "onReachedMaxAppCacheSize")
    }

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
//        Log.d("lol", "onProgressChanged")
    }

    override fun hashCode(): Int {
        Log.d("lol", "hashCode")
        return super.hashCode()
    }

    override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        Log.d("lol", "onJsConfirm")
        return super.onJsConfirm(view, url, message, result)
    }

    override fun getVisitedHistory(callback: ValueCallback<Array<String>>?) {
        super.getVisitedHistory(callback)
        Log.d("lol", "getVisitedHistory")
    }

    override fun equals(other: Any?): Boolean {
        Log.d("lol", "equals")
        return super.equals(other)
    }

    override fun getVideoLoadingProgressView(): View? {
        Log.d("lol", "getVideoLoadingProgressView")
        return super.getVideoLoadingProgressView()
    }

    override fun toString(): String {
        Log.d("lol", "toString")
        return super.toString()
    }

    override fun onGeolocationPermissionsHidePrompt() {
        super.onGeolocationPermissionsHidePrompt()
        Log.d("lol", "onGeolocationPermissionsHidePrompt")
    }

    override fun getDefaultVideoPoster(): Bitmap? {
        Log.d("lol", "getDefaultVideoPoster")
        return super.getDefaultVideoPoster()
    }

    override fun onJsBeforeUnload(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        Log.d("lol", "onJsBeforeUnload")
        return super.onJsBeforeUnload(view, url, message, result)
    }

    override fun onHideCustomView() {
        super.onHideCustomView()
        Log.d("lol", "onHideCustomView")
    }

    override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {
        Log.d("lol", "onCreateWindow")
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
    }

    override fun onCloseWindow(window: WebView?) {
        super.onCloseWindow(window)
        Log.d("lol", "onCloseWindow")
    }

    override fun onJsTimeout(): Boolean {
        Log.d("lol", "onJsTimeout")
        return super.onJsTimeout()
    }
}
