package cn.coolplay.mundonoticias

import android.graphics.Color
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import cn.coolplay.sdk.AdImaType
import cn.coolplay.sdk.CoolPlaySdk
import cn.coolplay.sdk.loader.vast.AdVastRequestBuilder
import cn.coolplay.sdk.ui.vast.video.VideoPlayerController
import cn.coolplay.sdk.utils.print


//import com.zeasn.ad.connector.ZeasnAd
//import com.zeasn.ad.connector.bean.ZeasnAdInfo
//import com.zeasn.ad.connector.bean.ZeasnError
//import com.zeasn.ad.connector.impl.AdType
//import com.zeasn.ad.connector.impl.PluginPlayerControl
//import com.zeasn.ad.connector.impl.ZeasnAdKey
//import com.zeasn.ad.connector.listener.ZeasnOnVodListener


class MainActivity : AppCompatActivity() {
    //    var bannerAdPC: PluginPlayerControl? = null
    private var adContainer1: FrameLayout? = null
    var adContainer2: FrameLayout? = null
    var adContainer: FrameLayout? = null

    //    val mHandle = Handler(Looper.getMainLooper())
//    private var bannerAd: ZeasnAd? = null
//    private val param by lazy {
//        val param: MutableMap<String, Any> = HashMap()
//        param[ZeasnAdKey.unitViewId] = "stb_banner_view"
//        param
//    }
    var adIndex = 0
    private val adRequestBuilder = AdVastRequestBuilder(
        this,
        testVastLink = "https://pubads.g.doubleclick.net/gampad/ads?iu=/21775744923/external/single_preroll_skippable&sz=640x480&ciu_szs=300x250%2C728x90&gdfp_req=1&output=vast&unviewed_position_start=1&env=vp&impl=s&correlator=",
        adImaType = AdImaType.SECTION,
    ) { adRequestBuilder, channel: String ->
        "channel:$channel".print("dddddd")
        if (channel == "google") {
            //这里可能会有多次回掉自己处理
            adContainer2?.let {
                adRequestBuilder.start(
                    it,
                    contentVideoUrl = "https://storage.googleapis.com/gvabox/media/samples/stock.mp4",
                )
            }
        } else {
            adContainer?.let {
                adRequestBuilder.start(
                    it,
                    contentVideoUrl = "https://storage.googleapis.com/gvabox/media/samples/stock.mp4",
                )
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adContainer1 = findViewById(R.id.adContainer1)
        adContainer1?.setOnFocusChangeListener { view, b ->
            view.setBackgroundColor(if (b) Color.BLUE else Color.RED)
            view.scaleY = if (b) 1.2f else 1f
        }
        adContainer1!!.setOnClickListener {
//            bannerAdPC?.option()
        }
        adContainer2 = findViewById(R.id.adContainer2)
        findViewById<FrameLayout>(R.id.adContainer2).setOnFocusChangeListener { view, b ->
            view.setBackgroundColor(if (b) Color.BLUE else Color.RED)
            view.scaleY = if (b) 1.2f else 1f
        }
        adContainer = findViewById(R.id.adContainer)
        adContainer?.setOnFocusChangeListener { view, b ->
            view.setBackgroundColor(if (b) Color.BLUE else Color.RED)
            view.scaleY = if (b) 1.2f else 1f
        }

//        loadAdBanner()

        CoolPlaySdk.loadAd(adRequestBuilder)
//        val controller =
//            VideoPlayerController(
//                adRequestBuilder,
//                "google",
//                0L,
//                4000L
//            )
//        controller.setCurrentAdTagUrl(adRequestBuilder.testVastLink!!)
//        controller.requestAndPlayAds()
//
//        val controller1 =
//            VideoPlayerController(
//                adRequestBuilder,
//                "miji",
//                0L,
//                4000L
//            )
//        controller1.setCurrentAdTagUrl(adRequestBuilder.testVastLink!!)
//        controller1.requestAndPlayAds()

//        val tvSdkView3 = VideoPlayerWithAdPlayback(this)
//        tvSdkView3.startAd(
//            contentUrl = "https://storage.googleapis.com/gvabox/media/samples/stock.mp4",
//            tagString = adRequestBuilder.testVastLink!!
//        )
//        tvSdkView3.aaaa = {
//            "zoulezhelu".print()
//            adContainer?.addView(tvSdkView3)
//            tvSdkView3.start {}
//
//        }
    }


//    val timeRun = Runnable {
//        bannerAd?.loadAd(param)
//    }

//    private fun loadAdBanner() {
//
//        bannerAd = ZeasnAd(this, adContainer1, AdType.Banner)
//
//        // 设置监听，⼀定要在请求⼴告之前，否则⼴告请求成功或者失败⽆回调
//        bannerAd?.listener = object : ZeasnOnVodListener {
//            override fun onVideoComplete(zeasnAdInfo: ZeasnAdInfo?) {
//                Log.e("MainActivity", "onVideoComplete")
//            }
//
//            override fun onAdSkip(adInfo: ZeasnAdInfo?) {
//                Log.e("MainActivity", "onAdSkip")
//            }
//
//            override fun onAdClick(adInfo: ZeasnAdInfo?) {
//                Log.e("MainActivity", "onAdClick")
//            }
//
//            override fun onAdLoaded(adInfo: ZeasnAdInfo?, playerControl: PluginPlayerControl) {
//                //PluginPlayerControl ⼴告控制器
//                bannerAdPC = playerControl
//                Log.e("MainActivity", "onAdLoaded")
//                if (bannerAdPC != null) {
//                    bannerAdPC?.start()
//                }
//            }
//
//            override fun onAdFailed(zeasnError: ZeasnError?) {
//                mHandle.postDelayed(timeRun, 10000)
//            }
//
//            override fun onAdClose(adInfo: ZeasnAdInfo?) {
//                mHandle.postDelayed(timeRun, 10000)
//            }
//        }
//        bannerAd?.loadAd(param)
//    }

    // 消亡banner广告，
    private fun destroyTpBanner() {
//        bannerAd?.release()
    }

    override fun onDestroy() {
        destroyTpBanner()
        super.onDestroy()
    }

}