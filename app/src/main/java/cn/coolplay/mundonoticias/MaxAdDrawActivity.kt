package cn.coolplay.mundonoticias

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView


class MaxAdDrawActivity : AppCompatActivity(), MaxAdViewAdListener {

    private var adContainer: FrameLayout? = null
    private var adView: MaxAdView? = null
    private var tvShow: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_max_ai_draw)
        adContainer = findViewById(R.id.adContainer)

        tvShow = findViewById(R.id.tv_show)
        adView = MaxAdView("d0ba481d6a8a2f10", MaxAdFormat.MREC, this)
        // Stretch to the width of the screen for banners to be fully functional
        // Get the adaptive banner height.

        adContainer?.post {
            val heightDp = adContainer?.measuredHeight ?: 0
            Log.i("Max", "Banner height: $heightDp")

            adView?.layoutParams = FrameLayout.LayoutParams(heightDp, heightDp)
            adContainer?.setBackgroundColor(Color.BLUE)
            adView?.setListener(this)
            tvShow?.setOnClickListener {
                adView?.loadAd()
            }
        }
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
        if (adView?.parent == null) {
            adContainer?.addView(adView)
        }
    }

    override fun onAdDisplayed(p0: MaxAd) {
        tvShow?.postDelayed({
            adContainer?.removeView(adView)
        }, 10000)

    }

    override fun onAdHidden(p0: MaxAd) {
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