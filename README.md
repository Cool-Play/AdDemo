# 1. 配置仓库地址
repositories {
   mavenCentral()
}

# 2. App's build.gradle 增加依赖包
```
dependencies {
// TradPlus
    implementation 'com.tradplusad:tradplus:12.6.10.1'
    //noinspection GradleCompatible
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.appcompat:appcompat:1.3.0-alpha02'
    // Admob
    implementation 'com.google.android.gms:play-services-ads:23.3.0'
    implementation 'com.tradplusad:tradplus-googlex:2.12.6.10.1'
    // Applovin
    implementation 'com.applovin:applovin-sdk:13.0.0'
    implementation 'com.tradplusad:tradplus-applovin:9.12.6.10.1'
    implementation 'com.google.android.gms:play-services-ads-identifier:17.0.0'
    // Cross Promotion
    implementation 'com.tradplusad:tradplus-crosspromotion:27.12.6.10.1'
    // TP Exchange
    // 请注意保持与主包版本同步更新
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.tradplusad:tp_exchange:40.12.6.10.1'

}
```
# 3. AndroidManifest.xml
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="your.app.package.name">
    <uses-permission android:name="android.permission.AD_ID" />
    <application>
        <!-- Google Play Services -->
        <meta-data
                android:name="com.google.android.gms.version"
                android:value="@integer/google_play_services_version" />
        <!-- 启用广告加载优化标志，以减少广告加载导致 ANR 的发生 -->
        <meta-data
               android:name="com.google.android.gms.ads.flag.OPTIMIZE_AD_LOADING"
                  android:value="true"/>

        <meta-data
                android:name="com.google.android.gms.ads.APPLICATION_ID"
                android:value="ca-app-pub-2540674760491959~8068485952"/>
        <!-- Google Ad Manager-->
        <meta-data
                android:name="com.google.android.gms.ads.AD_MANAGER_APP"
                android:value="true"/>
    </application>
</manifest>
```
# 4. 初始化代码
```
在application中初始化代码

package cn.coolplay.mundonoticias

import android.app.Application
import android.content.Context
import android.util.Log
import com.tradplus.ads.open.TradPlusSdk


class App : Application() {

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
```

# 5. 混淆配置
```
-keep public class com.tradplus.** { *; }
-keep class com.tradplus.ads.** { *; }
```
# 6. 开屏广告
```
splash.xml

<FrameLayout
        android:id="@+id/adContainer"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />


/**
 * 加载开屏广告
 */
private fun loadSplashAd() {
  
    TPSplash(this, "0B3FFB024DA1B8AF2AF0F6A312345678").apply {
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
/**
*. 消亡
**/
fun destroySplash() {
    adContainer?.removeAllViews()
    tpSplash?.onDestroy()
    tpSplash = null
}
```

# 7. 横幅广告
```
activity_main.xml
<FrameLayout
        android:id="@+id/adContainer"
        android:layout_gravity="center_horizontal"
        android:layout_width="wrap_content"
        android:layout_height="50dp" />


/**
 * --------------------------------------------------------------------------------------------------------------
 * banner的基本用法
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
                    "TradPlusLog",
                    "width=${width},screenWidth:---${it.measuredWidth}"
                )
               //这里做放大处理 使广告填充满两边 如果默认的这里不需要处理
                if (width > 0 && it.measuredWidth > 0) {
                    val scale = width / it.measuredWidth.toFloat()
                    //容器 view
                    adContainer?.scaleX = scale
                    adContainer?.scaleY = scale
                    Log.i("TradPlusLog", "${scale}")
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
        it.loadAd("7BA4665352D496EF3D6A433512345678");
    }
}

//  消亡banner广告，
private fun destroyTpBanner() {
    tpBanner?.also { adContainer?.removeView(it) }
    tpBanner?.onDestroy()
    tpBanner = null
}
```
# 8. 隐私合规 

隐私合规具体规范请参照 https://docs.tradplusad.com/docs/tradplussdk_android_doc_v6/privacy_policy/os_privacy_policy

