package jms.eqms.Sample

import com.google.gson.annotations.SerializedName

/**
 * Created by jmsbusinesssoftmac on 2017/12/13.
 */
data class Response(
    @SerializedName("wCode")
    var id:String,
    @SerializedName("eCode")
    var code:String
){
    constructor() : this("","")
}