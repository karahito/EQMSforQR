package jms.android.common.utility

import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by jmsbusinesssoftmac on 2017/12/14.
 */
class RestProvider {

    companion object {
        @JvmStatic
        fun newInstance():RestProvider = RestProvider()

        @JvmStatic var provider:Any? = null
        @JvmStatic
        inline fun <reified Interface> build(baseURL: String, callAdapter: CallAdapter.Factory, converter: Converter.Factory){
             val builider = Retrofit.Builder()
                    .client(OkHttpClient())
                    .baseUrl(baseURL)
                    .addCallAdapterFactory(callAdapter)
                    .addConverterFactory(converter)
                     .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Interface::class.java)
            provider = builider
        }
    }
}