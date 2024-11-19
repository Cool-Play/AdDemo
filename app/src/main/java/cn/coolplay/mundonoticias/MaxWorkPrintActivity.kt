package cn.coolplay.mundonoticias

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.sdk.AppLovinSdkUtils


class MaxWorkPrintActivity : AppCompatActivity(), MaxAdViewAdListener {

    private var adContainer: FrameLayout? = null
    private var adView: MaxAdView? = null
    private var webView: WebView? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_max_work_print)
        adContainer = findViewById(R.id.adContainer)
        webView = findViewById(R.id.webView)
        adView = MaxAdView("5b02581101095aa8", this)
        webView?.settings?.javaScriptEnabled = true//启用JavaScript的支持
        webView?.webViewClient = WebViewClient()//目标的网页仍然在当前WebView中显示
        webView?.loadUrl("https://www.baidu.com")
        adContainer?.post {
            val heightDp = MaxAdFormat.BANNER.getAdaptiveSize(this).height
            val heightPx = AppLovinSdkUtils.dpToPx(this, heightDp)
            adView?.layoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, heightPx)
            adView?.setListener(this)
            adView?.loadAd()
        }
        adContainer?.bringToFront()
    }

    // 消亡banner广告，
    private fun destroyTpBanner() {
        adView?.destroy()
    }

    override fun onDestroy() {
        destroyTpBanner()
        super.onDestroy()
    }

    override fun onAdLoaded(p0: MaxAd) {
        Log.e("TAG", "广告加载成功")
        if (adView?.parent == null) {
            adContainer?.addView(adView)
        }
    }

    override fun onAdDisplayed(p0: MaxAd) {
        Log.e("TAG", "广告显示成功")
    }

    override fun onAdHidden(p0: MaxAd) {
        Log.e("TAG", "广告隐藏成功")
    }

    override fun onAdClicked(p0: MaxAd) {

    }

    override fun onAdLoadFailed(p0: String, p1: MaxError) {
        adView?.loadAd()
    }

    override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
        adView?.loadAd()
    }

    override fun onAdExpanded(p0: MaxAd) {

    }

    override fun onAdCollapsed(p0: MaxAd) {
    }

}