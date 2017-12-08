package jms.eqms.Model.OnlineWork

import jms.eqms.Service.EqmsService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 */
class ClientProvider {
   companion object {
       @JvmStatic val client:EqmsService
       private val baseUrl = "http://192.168.1.208:8080"
       init {
           client = Retrofit.Builder()
                   .client(OkHttpClient())
                   .baseUrl(baseUrl)
                   .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                   .addConverterFactory(GsonConverterFactory.create())
                   .build()
                   .create(EqmsService::class.java)
       }
   }
}