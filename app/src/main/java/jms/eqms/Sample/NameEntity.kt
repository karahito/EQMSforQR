package jms.eqms.Sample

import com.google.gson.annotations.SerializedName
import pl.droidsonroids.jspoon.annotation.Selector

/**
 *
 */
class NameEntity (
    @SerializedName("NAME")
    val name:String = "",
    @SerializedName("ERROR")
    val error:String? = null)

class HtmlToJson(
    @Selector("body")
    val json:String
){
    constructor() : this("")
}