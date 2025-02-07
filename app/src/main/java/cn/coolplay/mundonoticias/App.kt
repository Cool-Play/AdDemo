package cn.coolplay.mundonoticias

import android.app.Application
import android.content.Context
import android.util.Log
import com.zeasn.ad.connector.ZeasnAdSDK
import com.zeasn.ad.connector.bean.AdBean
import com.zeasn.ad.connector.bean.ZeasnError




class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }


    override fun onCreate() {
        super.onCreate()
        initTPSDK()
    }

    private fun initTPSDK() {
        val beans: AdBean = AdBean.Builder()
            .deviceTypeValue("techvision_monitor")
            .brandId("101")
            .deviceSetId("10a6a5d4c1c2fa4550b4d86af4b441b67f")
            .functionType("TvLauncher")
            .isAdLogEnabled(true)
            .mac("08:00:27:DA:4D:A9")
            .productId("techvisionwhale")
            .sceneId("sceneId")
            .terminalType("TV")
            .build()

        ZeasnAdSDK.init(this, "acc", beans, object : ZeasnError() {
            override fun setData(data: Map<String, Any>) {
                super.setData(data)
                Log.e("init", data.toString())
            }

            override fun setMsg(msg: String) {
                super.setMsg(msg)
                Log.e("init_msg", msg.toString())
            }
        })
    }
}