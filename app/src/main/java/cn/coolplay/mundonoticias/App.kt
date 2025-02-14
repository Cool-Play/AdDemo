package cn.coolplay.mundonoticias

import android.app.Application
import android.content.Context
import cn.coolplay.sdk.CoolPlaySdk
import cn.coolplay.sdk.utils.print

//import com.zeasn.ad.connector.ZeasnAdSDK
//import com.zeasn.ad.connector.bean.AdBean
//import com.zeasn.ad.connector.bean.ZeasnError
//import com.zeasn.ad.connector.control.AdConstant


class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }


    override fun onCreate() {
        super.onCreate()
//        initTPSDK()
        CoolPlaySdk.init(
            this,
            "S754918136",
            "4f9de9cfdc4643e89e6d0d26a22828d5",
            packageName = "cn.coolplay.shortvideo",
            isDebug = true,
        ) {
            it.initStatusCallBack = { i: Int, s: String? ->
                "initSdk==$s".print()
            }
        }
    }

//    private fun initTPSDK() {
//        val beans: AdBean = AdBean.Builder()
//            .deviceTypeValue("WHALEOS_ZEASN_962D4_4K_P1")
//            .productId("wm100")
//            .brandId("7")
//            .mac("08:00:27:DA:4D:A9")
//            .functionType("TvLauncher")
//            .terminalType("TV")
//            .sceneId("STB_Launcher")
//            .isAdLogEnabled(true)
//            .build()
//        /**
//         * buildType : 可切换环境，acc测试环境，prod正式环境
//         */
//        ZeasnAdSDK.init(this, "prod", beans, object : ZeasnError() {
//            override fun setData(data: Map<String, Any>) {
//                super.setData(data)
//                Log.e("init", data.toString())
//            }
//
//            override fun setMsg(msg: String) {
//                super.setMsg(msg)
//                Log.e("init_msg", msg.toString())
//            }
//        })
//        //设置DP开关
//        ZeasnAdSDK.getInstance().setConfig(AdConstant.AD_KEY_SET_DP_SWITCH, true);
//        //设置广告管理URL
//        ZeasnAdSDK.getInstance().setConfig(
//            AdConstant.AD_KEY_SET_SAAS_URL,
//            "https://cache.zeasn.tv/webstatic/aosp_web/html/aosp-adentry.html?unitViewId=stb_banner_view|type=normal|unitId=STB_Coolplay_Home_Video_Landscape|type=normal|unitId=STB_Coolplay_Home_Display_Landscape|unitViewId=stb_fullscreen_view|type=normal|unitId=STB_Coolplay_Splash_Video&mute_area=EN|AR&banner_time=1%fs_time=1&cache_time=5&dp_time=2&skip_sec=5%overtime_sec=10&img_skip_sec=3&preload_pause_destroy_min=1&overtime_img_sec=7&recommend_ad=true&relevant_ads=[relevant_ads]&device_dnt=[device_dnt]&whale_ad_id=[whale_ad_id]&lang=[menu_lang]&platformid=[platformid]&cntry=[cntry]&profile_id=[profile_id]&mac=[mac]"
//        );
//    }
}