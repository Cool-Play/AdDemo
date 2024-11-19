package cn.coolplay.mundonoticias

import android.app.Application
import android.content.Context
import android.util.Log
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkInitializationConfiguration
import com.tradplus.ads.open.TradPlusSdk
import java.io.IOException
import java.util.Arrays
import java.util.concurrent.Executors


class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }


    override fun onCreate() {
        super.onCreate()
        initTPSDK()
        initAppLovin()
    }

    private fun initAppLovin() {
        val YOUR_SDK_KEY =
            "kZUEftzqIi7oaux7mXIcPcqbQJflAXf2TTnUZ05LZru2JP1HPrBTlqOlzvtNDWESCtbTWCZGNyzxT9pDw3OlEm"

        val executor = Executors.newSingleThreadExecutor();
        executor.execute {
            val initConfig = AppLovinSdkInitializationConfiguration.builder(YOUR_SDK_KEY, this)
                .setMediationProvider(AppLovinMediationProvider.MAX)
                .build()
            AppLovinSdk.getInstance(this).initialize(initConfig) {
                Log.i("Applovin", "onSdkInitialized")
            }
            executor.shutdown()
        }
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