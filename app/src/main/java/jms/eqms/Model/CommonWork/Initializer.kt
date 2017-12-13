package jms.eqms.Model.CommonWork

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import jms.android.common.LogUtil

/**
 *
 */
class Initializer {

    companion object {
        @JvmStatic
        fun newInstance(): Initializer = Initializer()
    }


    var disposable: CompositeDisposable = CompositeDisposable()

    fun <T>createObservable(initMethod:Single<T>):Single<T>{
//        disposable.add(initMethod as Disposable)
        return Single.create{
            initMethod.subscribeOn(Schedulers.single())
                    .subscribe{result: T, e: Throwable? ->
                        if (e != null) {
                            it.onError(e)
                        }else
                            it.onSuccess(result)
                    }
                    .addTo(disposable)
        }
    }

    fun dispose(){
        disposable.dispose()
        LogUtil.d("${disposable.isDisposed}")
    }
}