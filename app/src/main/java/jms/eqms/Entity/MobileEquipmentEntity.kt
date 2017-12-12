package jms.eqms.Entity

import com.bluelinelabs.logansquare.annotation.JsonObject
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 *  MobileDatabaseTable
 *  備品管理情報テーブル
 *
 *  @param code             @PrimaryKey 備品固有番号　
 *  @param worker           管理者
 *  @param latest_update    最終更新日
 *  @author D.Noguchi
 *  @Since 1,Nov.2017
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS)
@RealmClass
open class MobileEquipmentEntity(
        @PrimaryKey var code:String,
        var worker:String,
        var latest_update:String,
        var update_status:Int
):RealmModel{
    constructor() : this("","","",UpdateStatus.YET.value)
}

