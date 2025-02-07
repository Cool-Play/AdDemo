package cn.coolplay.mundonoticias

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.zeasn.ad.connector.ZeasnAd
import com.zeasn.ad.connector.bean.ZeasnAdInfo
import com.zeasn.ad.connector.bean.ZeasnError
import com.zeasn.ad.connector.impl.AdType
import com.zeasn.ad.connector.impl.PluginPlayerControl
import com.zeasn.ad.connector.impl.ZeasnAdKey
import com.zeasn.ad.connector.listener.ZeasnOnVodListener


class MainActivity : AppCompatActivity() {
    var bannerAdPC: PluginPlayerControl? = null
    private var adContainer1: FrameLayout? = null
    val mHandle = Handler(Looper.getMainLooper())
    private var bannerAd: ZeasnAd? = null
    private val param by lazy {
        val param: MutableMap<String, Any> = HashMap()
        param[ZeasnAdKey.unitViewId] = "unitViewId"
        param
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adContainer1 = findViewById(R.id.adContainer1)

        loadAdBanner()

    }

    val timeRun = Runnable {
        bannerAd?.loadAd(param)
    }

    private fun loadAdBanner() {

        bannerAd = ZeasnAd(this, adContainer1, AdType.Banner)

        // 设置监听，⼀定要在请求⼴告之前，否则⼴告请求成功或者失败⽆回调
        bannerAd?.listener = object : ZeasnOnVodListener {
            override fun onVideoComplete(zeasnAdInfo: ZeasnAdInfo) {
                Log.e("onVideoComplete", "onVideoComplete")
            }

            override fun onAdSkip(adInfo: ZeasnAdInfo) {
                Log.e("onAdSkip", "onAdSkip")
            }

            override fun onAdClick(adInfo: ZeasnAdInfo) {
                Log.e("onAdClick", "onAdClick")
            }

            override fun onAdLoaded(adInfo: ZeasnAdInfo, playerControl: PluginPlayerControl) {
                //PluginPlayerControl ⼴告控制器
                bannerAdPC = playerControl
                Log.e("onAdLoaded", "onAdLoaded")
                if (bannerAdPC != null) {
                    bannerAdPC?.start()
                }
            }

            override fun onAdFailed(zeasnError: ZeasnError) {
                mHandle.postDelayed(timeRun, 10000)
            }

            override fun onAdClose(adInfo: ZeasnAdInfo) {
                mHandle.postDelayed(timeRun, 10000)
            }
        }
        bannerAd?.loadAd(param)
    }

    // 消亡banner广告，
    private fun destroyTpBanner() {
        bannerAd?.release()
    }

    override fun onDestroy() {
        destroyTpBanner()
        super.onDestroy()
    }

}