package jms.eqms.Entity

import com.google.gson.annotations.SerializedName

/**
 * Created by jmsbusinesssoftmac on 2017/12/14.
 */
data class StockTake(
        @SerializedName("WCODE")
        val WCODE:String,
        @SerializedName("ECODE")
        val ECODE:Array<String>
)