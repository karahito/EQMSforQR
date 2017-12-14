package jms.eqms.Service

import io.reactivex.Single
import jms.eqms.Entity.JsonFromHtml
import jms.eqms.Entity.StockTake
import jms.eqms.Entity.UpdateEntity
import jms.eqms.Entity.WebClientResponse
import retrofit2.http.*

/**
 *
 */
interface EqmsService {

    @GET("/findAll")
    fun read(): Single<List<WebClientResponse>>

//
//    @Headers("Content-Type: application/json")
//    @POST("inventories")
//    fun transaction(@Body body:String):Single<JsonFromHtml>

    @GET("/inventories")
    fun transaction(@Query("WCODE") wCode: String,@Query("ECODE") eCode:Array<String>):Single<JsonFromHtml>

//    @GET("/inventories/?WCODE=151&ECODE=10000")
//    fun test():Single<String>
//
//    @GET("/inventories")
//    fun commit(@Query("WCODE") wCode: String,@Query("ECODE")eCode: String):Single<JsonFromHtml>
//
//    @FormUrlEncoded
//    @POST("/inventories")
//    fun post(@Field("WCODE") wCode:String,@Field("ECODE") eCode:String):Single<String>

    @GET("/inventories")
    fun login(@Query("WCODE") wCode:String): Single<JsonFromHtml>
}
