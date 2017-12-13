package jms.eqms.Sample

import com.google.gson.annotations.SerializedName
import pl.droidsonroids.jspoon.annotation.Selector

/**
 * Created by jmsbusinesssoftmac on 2017/12/13.
 */
class NameEntity (
    @SerializedName("NAME")
    val name:String){
    constructor() : this("")
}