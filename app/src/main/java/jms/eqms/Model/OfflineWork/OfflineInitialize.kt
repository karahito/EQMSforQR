package jms.eqms.Model.OfflineWork

import android.annotation.SuppressLint
import io.realm.Realm
import jms.eqms.Entity.MobileEquipmentEntity
import jms.eqms.Entity.UpdateStatus
import jms.eqms.Model.CommonWork.TakingDate

/**
 * @author D.Noguchi
 */
class OfflineInitialize {

    private val realm = Realm.getDefaultInstance()
    @SuppressLint("SimpleDateFormat")
    fun initialize(){
        val realmResults = realm.where(MobileEquipmentEntity::class.java).findAll()
        realmResults.forEach {
            if(!TakingDate.checkTakingDate(it.latest_update))
                it.update_status = UpdateStatus.ALREADY.value
            else
                it.update_status = UpdateStatus.YET.value
        }
    }
}