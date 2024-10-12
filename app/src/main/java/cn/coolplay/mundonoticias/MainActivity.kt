package cn.coolplay.mundonoticias

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tradplus.ads.base.bean.TPAdError
import com.tradplus.ads.base.bean.TPAdInfo
import com.tradplus.ads.open.banner.BannerAdListener
import com.tradplus.ads.open.banner.TPBanner


class MainActivity : AppCompatActivity() {

    private var adContainer: FrameLayout? = null
    private var tpBanner: TPBanner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adContainer = findViewById(R.id.adContainer)
        val llview: LinearLayout = findViewById(R.id.cp_rl_root)

        llview.post {
            loadBanner(llview.measuredWidth)
        }

    }

    /**
     * --------------------------------------------------------------------------------------------------------------
     * banner的基本用法，如果没有特殊需求，按照如下代码接入即可
     * width: Int = 0  // banner的宽度
     * --------------------------------------------------------------------------------------------------------------
     */
    private fun loadBanner(width: Int = 0) {
        TPBanner(this).also {
            tpBanner = it
            it.setAdListener(object : BannerAdListener() {
                override fun onAdClicked(tpAdInfo: TPAdInfo) {
                    Toast.makeText(this@MainActivity, "广告被点击了", Toast.LENGTH_SHORT).show()
                }

                override fun onAdImpression(tpAdInfo: TPAdInfo) {
                    Toast.makeText(this@MainActivity, "广告展示", Toast.LENGTH_SHORT).show()
                    Log.i(
                        "TradPlusLog", "width=${width},screenWidth:---${it.measuredWidth}"
                    )
                    //这里做放大处理 使广告填充满两边 如果默认的这里不需要处理
                    if (width > 0 && it.measuredWidth > 0) {
                        val scale = width / it.measuredWidth.toFloat()
                        adContainer?.scaleX = scale
                        adContainer?.scaleY = scale
                        Log.i("TradPlusLog", "$scale")
                    }
                }

                override fun onAdLoaded(tpAdInfo: TPAdInfo) {
                    Toast.makeText(this@MainActivity, "广告加载完成", Toast.LENGTH_SHORT).show()

                }

                override fun onAdLoadFailed(error: TPAdError) {
                    Toast.makeText(this@MainActivity, "广告加载失败", Toast.LENGTH_SHORT).show()
                }

                override fun onAdClosed(tpAdInfo: TPAdInfo) {
                    Toast.makeText(
                        this@MainActivity,
                        "onAdClosed:${tpAdInfo.adSourceName + "广告关闭"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
            adContainer?.addView(tpBanner)
            it.loadAd(BuildConfig.bannerId);
        }
    }

    // 消亡banner广告，
    private fun destroyTpBanner() {
        tpBanner?.also { adContainer?.removeView(it) }
        tpBanner?.onDestroy()
        tpBanner = null
    }

    override fun onDestroy() {
        destroyTpBanner()
        super.onDestroy()
    }

}