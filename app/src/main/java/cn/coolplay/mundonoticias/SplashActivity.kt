package cn.coolplay.mundonoticias

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.tradplus.ads.base.bean.TPAdError
import com.tradplus.ads.base.bean.TPAdInfo
import com.tradplus.ads.base.bean.TPBaseAd
import com.tradplus.ads.open.splash.SplashAdListener
import com.tradplus.ads.open.splash.TPSplash

class SplashActivity : AppCompatActivity() {
    private var tpSplash: TPSplash? = null
    var adContainer: FrameLayout? = null
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
        TPSplash(this, BuildConfig.spalashId).apply {
            tpSplash = this
            // 设置监听
            setAdListener(object : SplashAdListener() {
                // 广告加载完成 首个广告源加载成功时回调 一次加载流程只会回调一次
                override fun onAdLoaded(tpAdInfo: TPAdInfo?, tpBaseAd: TPBaseAd?) {
                    showSplashAd()
                }

                // 广告被点击
                override fun onAdClicked(tpAdInfo: TPAdInfo?) {}

                // 广告成功展示在页面上
                override fun onAdImpression(tpAdInfo: TPAdInfo?) {

                }

                // 广告加载失败
                override fun onAdLoadFailed(error: TPAdError?) {
                    goMain()
                }

                // 广告被关闭
                override fun onAdClosed(tpAdInfo: TPAdInfo?) {
                    destroySplash()
                    goMain()
                }

            })
            loadAd(null);
        }
    }

    /**
     *  显示开屏广告 监听到onAdLoaded回调后调用
     */
    fun showSplashAd() {
        if (tpSplash?.isReady == true) {
            tpSplash?.showAd(adContainer)
        }
    }

    fun destroySplash() {
        adContainer?.removeAllViews()
        tpSplash?.onDestroy()
        tpSplash = null
    }

    private fun goMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}