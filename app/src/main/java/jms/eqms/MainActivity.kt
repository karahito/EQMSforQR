package jms.eqms

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.jakewharton.rxbinding.widget.checked
import com.jakewharton.rxbinding.widget.checkedChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import jms.android.common.LogUtil
import jms.android.common.utility.NetworkReceiver
import jms.eqms.Entity.UpdateEntity
import jms.eqms.Model.CommonWork.CreateJson
import jms.eqms.Model.OfflineWork.OfflineInitialize
import jms.eqms.Model.OnlineWork.ClientProvider
import jms.eqms.Model.OnlineWork.OnlineInitialize
import jms.eqms.UI.EquipmentListFragment
import jms.eqms.UI.HistoryFragment


class MainActivity : AppCompatActivity(),NetworkReceiver.OnNetworkStateListener, OnlineInitialize.OnInitializedListener {

    override fun onInitEnd(){
        progress.dismiss()
//        supportFragmentManager.beginTransaction()
//                .add(R.id.tab1,EquipmentListFragment())
//                .commit()
        val adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {

//            return EquipmentListFragment.newInstance()
                return if(position == 0){
                    EquipmentListFragment.newInstance()
                }else
                    HistoryFragment.newInstance()
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return pageTitle[position]
            }

            override fun getCount(): Int {
                return pageTitle.count()
            }
        }

        mViewPager.adapter =adapter
        mTabLayout.setupWithViewPager(mViewPager)
    }
    override fun changeToOffline() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        if (mConSwitch.isChecked) {
            mConSwitch.checkedChanges()
            mConSwitch.text = OFFLINE
            mConSwitch.invalidate()
        }
    }

    override fun changeToMobile(info: NetworkInfo) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        if (mConSwitch.isChecked) {
            mConSwitch.checkedChanges()
            mConSwitch.text = OFFLINE
            mConSwitch.invalidate()
        }
    }

    override fun changeToWifi(info: NetworkInfo) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        mConSwitch.text = ONLINE
        mConSwitch.checked()
        mConSwitch.invalidate()
    }

    companion object {
        private val ACCEPT_SSID = """"JMS-SOFT""""
        private val ONLINE = "ONLINE"
        private val OFFLINE = "OFFLINE"
        val pageTitle = arrayOf("List", "History")

    }
    @BindView(R.id.switch1) lateinit var mConSwitch:Switch
    @BindView(R.id.tabLayout) lateinit var mTabLayout:TabLayout
    @BindView(R.id.viewPager) lateinit var mViewPager:ViewPager
    @BindView(R.id.fabCamera) lateinit var mCamera:FloatingActionButton
    @BindView(R.id.input_modified) lateinit var mModifedBy:EditText

    private val mReceiver = NetworkReceiver(this)
    private val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

    private var conMgr:ConnectivityManager? = null
    private var netInfo:NetworkInfo? = null
    private lateinit var progress:ProgressDialog
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        Realm.init(this)



        progress = ProgressDialog (this)
        progress.setCancelable(false)
        progress.show()
        conMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        netInfo = conMgr?.activeNetworkInfo
        if (netInfo?.type == TYPE_WIFI) {
            val wifiMgr = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiMgr.connectionInfo
            if (wifiInfo.ssid == ACCEPT_SSID) {
                OnlineInitialize().initialize(this)
                mConSwitch.text = ONLINE
                mConSwitch.checked()
            }else {
                OfflineInitialize().initialize()
                mConSwitch.text= OFFLINE
            }
        }else {
            OfflineInitialize().initialize()
            mConSwitch.text= OFFLINE
        }


        mConSwitch.setOnCheckedChangeListener { _, check ->
            if (check) {
                mConSwitch.text = ONLINE
            }else
                mConSwitch.text = OFFLINE

            mConSwitch.invalidate()
        }

        mCamera.setOnClickListener {
            if (mModifedBy.text.length == 3) {
                IntentIntegrator(this).initiateScan()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        registerReceiver(mReceiver,filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiver)
    }

    /**
     *  他のアクティビティからのデータ (Intent)を受け取る
     *  カメラから読取結果を受けとる
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            progress.show()
            val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            val code = intentResult.contents.lines()[0]
            val sjson = CreateJson.createUpdateJson(code,mModifedBy.text.toString())
            ClientProvider.client.transaction(sjson)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        /** onSuccess*/
                        {
                            progress.dismiss()
                            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                        },
                        /** onError*/
                        {
                            progress.dismiss()
                            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                        }
                    )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.beginTransaction()
                .remove(EquipmentListFragment())
                .commit()
    }

}
