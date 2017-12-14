package jms.eqms.Sample

import android.text.Html
import io.reactivex.Single
import jms.android.common.LogUtil
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 *
 */
interface Service {
    @GET("/inventories")
    fun read(@Query("WCODE") wCode:String): Single<HtmlToJson>

    @GET("/inventories/?WCODE=151")
    fun readModel():Call<String>


}