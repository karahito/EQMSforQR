package jms.eqms.Model.OnlineWork

import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.exceptions.RealmException
import jms.android.common.LogUtil
import jms.eqms.Entity.MobileEquipmentEntity
import jms.eqms.Model.CommonWork.TakingDate


/**
 *
 */
open class OnlineInitialize {
    interface OnInitializedListener{
        fun onInitEnd()
    }
    companion object {
        private val webClient = ClientProvider.client
    }
    fun initialize(callback:Any){

        if (callback !is OnInitializedListener){
            throw ClassCastException("callback is implemented OnInitializedListener")
        }else {
            webClient.read()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            /** onSuccess*/
                            {
                                LogUtil.d("onSuccess:$it")
                                val realm = Realm.getDefaultInstance()
                                try {
                                    realm.beginTransaction()
                                    it.forEach {
                                        it.update_status = TakingDate.getUpdateStatus(it.latest_update, it.update_status)
                                    }
                                    realm.createOrUpdateAllFromJson(MobileEquipmentEntity::class.java, Gson().toJson(it))

                                } catch (e: RealmException) {
                                    LogUtil.e(e)
                                    callback.onInitEnd()
                                } finally {
                                    realm.commitTransaction()
                                    realm.close()
                                    callback.onInitEnd()
                                }
                            },
                            /** onError */
                            {
                                LogUtil.e(it)
                                callback.onInitEnd()
                            })
        }
    }
}