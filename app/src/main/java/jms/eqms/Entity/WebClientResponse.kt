package jms.eqms.Entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by jmsbusinesssoftmac on 2017/12/08.
 */
class WebClientResponse {
    @SerializedName("code")
    var code: String = ""
    @SerializedName("worker")
    var worker: String = ""
    @SerializedName("latest_update")
    var latest_update: String = ""
    @SerializedName("update_status")
    var update_status: Int = 0
}