package jms.eqms.Model.OfflineWork

import android.annotation.SuppressLint
import io.reactivex.Single
import io.realm.Realm
import io.realm.exceptions.RealmException
import jms.android.common.LogUtil
import jms.eqms.Entity.MobileEquipmentEntity
import jms.eqms.Entity.UpdateStatus
import jms.eqms.Model.CommonWork.TakingDate

/**
 * @author D.Noguchi
 */
class OfflineInitialize {
    companion object {
        @JvmStatic fun newInstance():OfflineInitialize = OfflineInitialize()
    }

    fun createSingle(): Single<Unit>{
        return Single.create{
            val realm = Realm.getDefaultInstance()
            try{
                val realmResults = realm.where(MobileEquipmentEntity::class.java).findAll()
                realm.beginTransaction()
                realmResults.forEach {
                    if (TakingDate.checkTakingDate(it.latest_update))
                        it.update_status = UpdateStatus.ALREADY.value
                    else
                        it.update_status = UpdateStatus.YET.value
                }
                it.onSuccess(Unit)
            }catch (e:RealmException){
                LogUtil.e(e)
                it.onError(e)
            }catch (e:Exception){
                LogUtil.e(e)
                it.onError(e)
            }finally {
                realm.commitTransaction()
                realm.close()
            }
        }
    }
}