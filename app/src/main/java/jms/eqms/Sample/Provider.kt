package jms.eqms.Sample

import android.text.Html
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.Observables
import jms.android.common.LogUtil
import okhttp3.OkHttpClient
import okhttp3.Request
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by jmsbusinesssoftmac on 2017/12/13.
 */
class Provider{
    companion object {
        private val baseURL="http://192.168.1.11"
//        private val baseURL = "http://api.openweathermap.org/data/2.5/weather?id=1850147&units=metric&"
        @JvmStatic val client:Service
        init{
            client = Retrofit.Builder()
                    .client(OkHttpClient())
                    .baseUrl(baseURL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(JspoonConverterFactory.create())
                    .build()
                    .create(Service::class.java)
        }
    }

    private fun getApi(url:String):String{
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(url)
                .build()
        val response = client.newCall(request).execute()
        return response.body()?.string() ?: throw ClassCastException("return error")
    }

    fun getName(url:String):Single<String>{
        return Single.create{
            try{
                val result = getApi(url)
                LogUtil.d(result)
                it.onSuccess(result)
            }catch (e:Exception){
                it.onError(e)
            }
        }
    }
}