package jms.eqms.Model.OnlineWork

import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import jms.android.common.LogUtil
import jms.android.common.utility.RestProvider
import jms.eqms.Entity.LoginResponse
import jms.eqms.Service.EqmsService
import jms.eqms.UI.Stock
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.NullPointerException

/**
 *
 */
class NetworkApi {
    companion object {
        @JvmStatic
        fun newInstance():NetworkApi = NetworkApi()
        init {
            val baseURL = "http://192.168.1.11"
            RestProvider.build<EqmsService>(baseURL, RxJava2CallAdapterFactory.create(), JspoonConverterFactory.create())
        }
        @JvmStatic
        val client = RestProvider.provider as EqmsService
        @JvmStatic
        val compositeDipose = CompositeDisposable()
    }

    fun login(wCode:String):Single<Unit> {
        lateinit var subscription: Disposable

        return Single.create {
            subscription = client.login(wCode)
                    .subscribeOn(Schedulers.single())
                    .subscribe(

                            /** onSuccess*/
                            {
                                try {
                                    val parse = Gson().fromJson(it.json, LoginResponse::class.java)
                                    if (parse.error != null || parse.wName == null) {
                                        LogUtil.d(parse.error)
                                    } else {
                                        LogUtil.d(parse.wName)
                                    }
                                } catch (e: NullPointerException) {
                                    LogUtil.e(e)
                                }finally {
                                    subscription.dispose()
                                }
                            },
                            /** onError*/
                            {
                                LogUtil.e(it)
                            }
                    )
        }

    }

    fun transaction(wCode:String,eCode:Array<String>):Single<String>{
        lateinit var subscription: Disposable

        return Single.create { emitter->
          subscription = client.transaction(wCode,eCode)
                    .subscribeOn(Schedulers.single())
                    .subscribe(
                            /** onSuccess*/
                            {
                               try{
                                   if (!it.json.isBlank()) {
                                       val parse = Gson().fromJson(it.json, Stock::class.java)
                                       LogUtil.d(parse.error)
                                       LogUtil.d("${parse.ecode?.forEach { it }}")
                                       emitter.onSuccess(it.json)
                                   }else
                                       emitter.onSuccess("Success")
                               }catch (e:NullPointerException){
                                   LogUtil.e(e)
                                   emitter.onError(e)
                               }finally {
                                   subscription.dispose()
                               }
                            },
                            /** onError */
                            {
                                emitter.onError(it)
                                subscription.dispose()
                            }
                    )
//                    .addTo(compositeDipose)
        }
    }
}