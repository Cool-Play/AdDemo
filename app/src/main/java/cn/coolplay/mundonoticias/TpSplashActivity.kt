package cn.coolplay.mundonoticias

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.tradplus.ads.base.bean.TPAdError
import com.tradplus.ads.base.bean.TPAdInfo
import com.tradplus.ads.open.nativead.NativeSplashAdListener
import com.tradplus.ads.open.nativead.TPNativeSplash

class TpSplashActivity : AppCompatActivity() {
    private var tpSplash: TPNativeSplash? = null
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
        TPNativeSplash(this).apply {
            tpSplash = this
            // 设置监听
            setAdListener(object : NativeSplashAdListener() {
                override fun onClickSkip(tpAdInfo: TPAdInfo?) {
                    super.onClickSkip(tpAdInfo)
                }

                override fun onAdShowFailed(error: TPAdError?, tpAdInfo: TPAdInfo?) {
                    super.onAdShowFailed(error, tpAdInfo)
                }

                override fun onAdClosed(tpAdInfo: TPAdInfo?) {
                    // 注意需要从容器中remove掉TPNativeSplash
                    adContainer?.removeAllViews()
                }
            })
            loadAd("86103D844745E9E2237814E5BEF6E340");

            // 6、添加tpSplash到容器中
            adContainer?.addView(tpSplash);

        }
    }

    fun destroySplash() {
        tpSplash?.onDestroy()
        tpSplash = null
    }

    private fun goMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}