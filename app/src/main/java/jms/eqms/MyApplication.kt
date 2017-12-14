package jms.eqms

import android.app.Application
import io.realm.Realm
import jms.android.common.utility.RestProvider
import jms.eqms.Service.EqmsService
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 *
 */
class MyApplication:Application() {
    init {
        val baseURL = "http://192.168.1.11"
        RestProvider.build<EqmsService>(baseURL, RxJava2CallAdapterFactory.create(), JspoonConverterFactory.create())
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this.applicationContext)
    }
}