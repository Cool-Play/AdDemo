package cn.coolplay.mundonoticias

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.zeasn.ad.connector.ZeasnAd
import com.zeasn.ad.connector.bean.ZeasnAdInfo
import com.zeasn.ad.connector.bean.ZeasnError
import com.zeasn.ad.connector.impl.AdType
import com.zeasn.ad.connector.impl.PluginPlayerControl
import com.zeasn.ad.connector.impl.ZeasnAdKey
import com.zeasn.ad.connector.listener.ZeasnListener
import com.zeasn.ad.connector.listener.ZeasnOnVodListener


class SplashActivity : AppCompatActivity() {
    private var adContainer: FrameLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        adContainer = findViewById(R.id.adContainer)
        loadSplashAd()
    }

    var fullscreenAdPC: PluginPlayerControl? = null

    /**
     * 加载开屏广告
     */
    private fun loadSplashAd() {
        val param: MutableMap<String, Any> = HashMap()
        param.put(ZeasnAdKey.pkgName, packageName);
        param.put(ZeasnAdKey.useActivity, true);
        param.put(ZeasnAdKey.unitViewId, "stb_fullscreen_view");
        val fullscreenAd = ZeasnAd(this, null, AdType.Fullscreen)
        fullscreenAd.listener = object : ZeasnOnVodListener {
            override fun onVideoComplete(zeasnAdInfo: ZeasnAdInfo?) {
                Log.e("SplashActivity", "onVideoComplete")
            }

            override fun onAdSkip(adInfo: ZeasnAdInfo?) {
                Log.e("SplashActivity", "onAdSkip")
            }

            override fun onAdClick(adInfo: ZeasnAdInfo?) {
                Log.e("SplashActivity", "onAdClick")
            }

            override fun onAdLoaded(adInfo: ZeasnAdInfo?, playerControl: PluginPlayerControl) {
                Log.e("SplashActivity", "onAdLoaded")
                //PluginPlayerControl ⼴告控制器
//                fullscreenAdPC = playerControl
//                Log.e("SplashActivity", "onAdLoaded== $fullscreenAdPC")
//                if (fullscreenAdPC != null) {
//                    fullscreenAdPC?.start()
//                }
            }

            override fun onAdFailed(zeasnError: ZeasnError?) {
                Log.e("SplashActivity", "onAdFailed")
                goMain()
            }

            override fun onAdClose(adInfo: ZeasnAdInfo?) {
                Log.e("SplashActivity", "onAdLoaded")
                goMain()
            }
        }

        fullscreenAd.loadAd(param)
    }

    fun destroySplash() {
        fullscreenAdPC?.release()
    }

    private fun goMain() {
        Log.e("SplashActivity", "goMain")
        destroySplash()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}