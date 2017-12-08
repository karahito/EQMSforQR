package jms.android.common.utility

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import jms.android.common.LogUtil

/**
 *
 */
class NetworkReceiver(private val callback:Context):BroadcastReceiver() {

    interface OnNetworkStateListener{
        fun changeToWifi(info:NetworkInfo)
        fun changeToMobile(info:NetworkInfo)
        fun changeToOffline()
    }
    @SuppressLint("ServiceCast", "MissingPermission")
    override fun onReceive(context: Context, intent: Intent?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val info = manager?.activeNetworkInfo
        when{
            info?.type == ConnectivityManager.TYPE_MOBILE -> {
                LogUtil.d("Network State has changed to Mobile")
                if (callback !is OnNetworkStateListener)
                    throw ClassCastException("callback source is do not implemented OnNetworkStateListener")
                else
                    callback.changeToMobile(info)
            }
            info?.type == ConnectivityManager.TYPE_WIFI -> {
                LogUtil.d("Network State has changed to WiFi")
                if (callback !is OnNetworkStateListener)
                    throw ClassCastException("callback source is do not implemented OnNetworkStateListener")
                else
                    callback.changeToWifi(info)
            }
            info == null -> {
                LogUtil.d("Network has not found")
                if (callback !is OnNetworkStateListener)
                    throw ClassCastException("callback source is do not implemented OnNetworkStateListener")
                else
                    callback.changeToOffline()
            }
        }
    }

}