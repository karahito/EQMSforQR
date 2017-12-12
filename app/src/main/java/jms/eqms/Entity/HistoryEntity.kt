package jms.eqms.Entity

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by jmsbusinesssoftmac on 2017/12/12.
 */
@RealmClass
open class HistoryEntity(
        @PrimaryKey var code:String,
        var worker:String,
        var modified_by:String,
        var update_date:String,
        var update_type:Int
):RealmModel{
    constructor() : this("","","","",UpdateType.WIFI.value)
}