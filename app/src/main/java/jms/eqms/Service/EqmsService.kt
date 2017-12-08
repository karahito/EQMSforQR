package jms.eqms.Service

import io.reactivex.Single
import jms.eqms.Entity.WebClientResponse
import retrofit2.http.GET

/**
 * Created by jmsbusinesssoftmac on 2017/12/07.
 */
interface EqmsService {

    @GET("/findAll")
    fun read(): Single<List<WebClientResponse>>
}