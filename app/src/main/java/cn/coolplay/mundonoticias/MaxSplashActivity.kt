package cn.coolplay.mundonoticias

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxAdRevenueListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAppOpenAd
import com.tradplus.ads.base.bean.TPAdError
import com.tradplus.ads.base.bean.TPAdInfo
import com.tradplus.ads.open.nativead.NativeSplashAdListener
import com.tradplus.ads.open.nativead.TPNativeSplash

class MaxSplashActivity : AppCompatActivity(), MaxAdListener {
    var adContainer: FrameLayout? = null
    var appOpenAd: MaxAppOpenAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        adContainer = findViewById(R.id.adContainer)
        loadSplashAd()
    }

    /**
     * 加载开屏广告
     */
    private fun loadSplashAd() {
        appOpenAd = MaxAppOpenAd("3ca9a73c64272b84", this)
        appOpenAd?.setListener(this)
        appOpenAd?.loadAd();
    }

    private fun showAd() {
        if (appOpenAd?.isReady == true) {
            appOpenAd?.showAd()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appOpenAd?.destroy()
        appOpenAd = null
    }

    private fun goMain() {
        startActivity(Intent(this, MaxMainActivity::class.java))
        finish()
    }

    override fun onAdLoaded(p0: MaxAd) {
        showAd()
    }

    override fun onAdDisplayed(p0: MaxAd) {

    }

    override fun onAdHidden(p0: MaxAd) {
        goMain()
    }

    override fun onAdClicked(p0: MaxAd) {

    }

    override fun onAdLoadFailed(p0: String, p1: MaxError) {
        goMain()
    }

    override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
        goMain()
    }

}