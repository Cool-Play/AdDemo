package cn.coolplay.mundonoticias

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tradplus.ads.base.bean.TPAdError
import com.tradplus.ads.base.bean.TPAdInfo
import com.tradplus.ads.base.bean.TPBaseAd
import com.tradplus.ads.open.nativead.NativeAdListener
import com.tradplus.ads.open.nativead.TPNative


class TPNativeActivity : AppCompatActivity() {
    private var adContainer: FrameLayout? = null
    private var tpNative: TPNative? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * --------------------------------------------------------------------------------------------------------------
     * native的基本用法，如果没有特殊需求，按照如下代码接入即可
     * --------------------------------------------------------------------------------------------------------------
     */
    private fun loadNormalNative() {
        TPNative(this, "TestAdUnitId.NATIVE_ADUNITID").apply {
            tpNative = this
            setAdListener(object : NativeAdListener() {
                override fun onAdLoaded(tpAdInfo: TPAdInfo, tpBaseAd: TPBaseAd) {
                    Log.i("TPTPNativeActivity", "onAdLoaded: " + tpAdInfo.adSourceName + "加载成功")
                    Toast.makeText(
                        this@TPNativeActivity,
                        "广告加载完成${tpAdInfo.adSourceName}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    showTpNative()
                }

                override fun onAdClicked(tpAdInfo: TPAdInfo) {
                    Toast.makeText(
                        this@TPNativeActivity,
                        "广告被点击${tpAdInfo.adSourceName}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAdImpression(tpAdInfo: TPAdInfo) {
                    Log.i("TPTPNativeActivity", "onAdImpression: " + tpAdInfo.adSourceName + "展示")
                    Toast.makeText(
                        this@TPNativeActivity,
                        "广告展示${tpAdInfo.adSourceName}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAdShowFailed(tpAdError: TPAdError, tpAdInfo: TPAdInfo) {
                    Log.i(
                        "TPTPNativeActivity",
                        "onAdShowFailed: " + tpAdInfo.adSourceName + "展示失败"
                    )
                }

                override fun onAdLoadFailed(tpAdError: TPAdError) {
                    Log.i(
                        "TPTPNativeActivity",
                        "onAdLoadFailed: 加载失败 , code : " + tpAdError.errorCode + ", msg :" + tpAdError.errorMsg
                    )
                    Toast.makeText(this@TPNativeActivity, "广告加载失败", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAdClosed(tpAdInfo: TPAdInfo) {
                    Log.i("TPTPNativeActivity", "onAdClosed: " + tpAdInfo.adSourceName + "广告关闭")
                }
            })
        }
    }

    /**
     *  显示开屏广告 监听到onAdLoaded回调后调用
     */
    fun showTpNative() {
        if (tpNative?.isReady == true) {
            tpNative?.showAd(adContainer, com.tradplus.ads.open.R.layout.tp_native_ad_list_item)
        }
    }

    fun destroyTpNative() {
        adContainer?.removeAllViews()
        tpNative?.onDestroy()
        tpNative = null
    }

}