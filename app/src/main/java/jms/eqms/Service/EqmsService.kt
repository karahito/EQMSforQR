package jms.eqms.Service

import io.reactivex.Single
import jms.eqms.Entity.UpdateEntity
import jms.eqms.Entity.WebClientResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 *
 */
interface EqmsService {

    @GET("/findAll")
    fun read(): Single<List<WebClientResponse>>


    @POST("/transaction")
    fun transaction(@Body sJson:UpdateEntity):Single<Boolean>
}
