package jms.android.common.utility

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 *
 */
class RxRetrofitProvider {
    companion object {
        @JvmStatic
        inline fun <reified T> getAPI(baseURL:String): T {
            return builder(baseURL)
        }

        inline fun <reified T> builder(baseURL: String): T {
            return Retrofit.Builder()
                    .client(OkHttpClient())
                    .baseUrl(baseURL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(Gson()))
                    .build()
                    .create(T::class.java)
        }
    }
}