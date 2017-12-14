package jms.eqms.Entity

import com.google.gson.annotations.SerializedName

/**
 *
 */
data class LoginResponse(
        @SerializedName("NAME")
        val wName:String? = null,
        @SerializedName("ERROR")
        val error:String? = null
)