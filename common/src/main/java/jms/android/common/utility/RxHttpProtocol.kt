package jms.android.common.utility

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Subscription


/**
 *
 */
class RxHttpProtocol {

     inline fun <reified Service> client(baseUrl:String):Service{
        val gson = Gson()

        val okClient = OkHttpClient()
        val builder = Retrofit.Builder()
                .client(okClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .build()

        return builder.create(Service::class.java)

    }
}

/**
 *  Observable上でonNext,onError,onCompleteを直接呼び出すための拡張関数
 *
 */
fun <T> Observable<T>.onNext(block:(T)->Unit):ExtSubscription<T> =
        ExtSubscription(this).onNext(block)

fun <T> Observable<T>.onError(block :(Throwable) -> Unit):ExtSubscription<T> =
        ExtSubscription(this).onError(block)

fun <T> Observable<T>.onComplete(block: ()-> Unit):ExtSubscription<T> =
        ExtSubscription(this).onComplete(block)

fun Subscription.onError(block: (Throwable) -> Unit):Subscription = this

class ExtSubscription<T>(val observable: Observable<T>){

    private var next:(T)->Unit = {}

    private var error:(Throwable) -> Unit = {throw it}

    private var complete:()->Unit={}
    fun onNext(block:(T) -> Unit):ExtSubscription<T>{
        next = block
        return this
    }

    fun onError(block: (Throwable) -> Unit):ExtSubscription<T>{
        error = block
        return this
    }

    fun onComplete(block:()->Unit):ExtSubscription<T>{
        complete = block
        return this
    }

    fun  subscribe():Subscription = observable.subscribe(
            /** onNext */
            {
                next.invoke(it)
            },
            {
                error.invoke(it)
            },
            {
                complete.invoke()
            })
}