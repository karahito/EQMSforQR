package jms.eqms

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import jms.android.common.utility.NetworkReceiver
import jms.eqms.Model.OfflineWork.OfflineInitialize
import jms.eqms.Model.OnlineWork.OnlineInitialize
import jms.eqms.UI.EquipmentListFragment

class MainActivity : AppCompatActivity(),NetworkReceiver.OnNetworkStateListener, OnlineInitialize.OnInitializedListener {

    override fun onInitEnd(){
        progress.dismiss()
        supportFragmentManager.beginTransaction()
                .add(R.id.listContents,EquipmentListFragment())
                .commit()

    }
    override fun changeToOffline() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeToMobile(info: NetworkInfo) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun changeToWifi(info: NetworkInfo) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val ACCEPT_SSID = """"JMS-SOFT""""
    }
    private val mReceiver = NetworkReceiver(this)
    private val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

    private var conMgr:ConnectivityManager? = null
    private var netInfo:NetworkInfo? = null
    private lateinit var progress:ProgressDialog
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)
        progress = ProgressDialog (this)
        progress.setCancelable(false)
        progress.show()
        conMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        netInfo = conMgr?.activeNetworkInfo
        if (netInfo?.type == TYPE_WIFI) {
            val wifiMgr = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiMgr.connectionInfo
            if (wifiInfo.ssid == ACCEPT_SSID)
                OnlineInitialize().initialize(this)
            else
                OfflineInitialize().initialize()
        }else
            OfflineInitialize().initialize()
//        progress.dismiss()


    }

    override fun onResume() {
        super.onResume()
        registerReceiver(mReceiver,filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.beginTransaction()
                .remove(EquipmentListFragment())
                .commit()
    }

}
