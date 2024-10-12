package cn.coolplay.mundonoticias

import android.app.Application
import android.content.Context
import android.util.Log
import com.tradplus.ads.open.TradPlusSdk


class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }


    override fun onCreate() {
        super.onCreate()
        initTPSDK()
    }

    private fun initTPSDK() {
        if (!TradPlusSdk.getIsInit()) {
            // 初始化是否成功 （可选）
            TradPlusSdk.setTradPlusInitListener { Log.i("TradPlusLog", "onInitSuccess: ") }
            // 初始化SDK
            TradPlusSdk.initSdk(this, BuildConfig.appId)
        }
    }
}