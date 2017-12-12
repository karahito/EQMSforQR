package jms.eqms.Service

import com.google.gson.JsonObject
import io.reactivex.Single
import jms.eqms.Entity.WebClientResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by jmsbusinesssoftmac on 2017/12/07.
 */
interface EqmsService {

    @GET("/findAll")
    fun read(): Single<List<WebClientResponse>>

    @POST("/transaction")
    fun transaction(@Body sJson:String):Single<Boolean>
}