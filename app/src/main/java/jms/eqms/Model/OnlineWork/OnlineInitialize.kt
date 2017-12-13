package jms.eqms.Model.OnlineWork

import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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
        fun newInstance():OnlineInitialize = OnlineInitialize()

        private val webClient = ClientProvider.client
        private var disposable:Disposable? = null
    }
    fun initialize(callback:Any){

        if (callback !is OnInitializedListener){
            throw ClassCastException("callback is implemented OnInitializedListener")
        }else {
            disposable = webClient.read()
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

    fun createSingle():Single<Unit>{
        return Single.create{ emitter->
            webClient.read().subscribe(
                    /** onSuccess */
                    {
                        LogUtil.d("onSuccess:$it")
                        val realm = Realm.getDefaultInstance()
                        try{
                            realm.beginTransaction()
                            it.forEach {
                                it.update_status = TakingDate.getUpdateStatus(it.latest_update, it.update_status)
                            }
                            realm.createOrUpdateAllFromJson(MobileEquipmentEntity::class.java, Gson().toJson(it))
                            emitter.onSuccess(Unit)
                        }catch (e:RealmException){
                            emitter.onError(e)
                        }catch (e:Exception) {
                            emitter.onError(e)
                        }finally {
                            realm.commitTransaction()
                            realm.close()
                        }
                    },
                    /** onError */
                    {
                        LogUtil.e("onError:",it)
                        emitter.onError(it)
                    })
        }
    }
    fun dispose(){
        disposable?.dispose() ?: return
        LogUtil.d("${disposable?.isDisposed}")
    }
}