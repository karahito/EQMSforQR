package jms.eqms

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.google.zxing.integration.android.IntentIntegrator
import jms.eqms.UI.EquipmentListFragment
import jms.eqms.UI.HistoryFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_list -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.contents, list)
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_camera -> {
                IntentIntegrator(this).initiateScan()
                return@OnNavigationItemSelectedListener false

            }
            R.id.navigation_history -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.contents, history)
                        .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    companion object {
        @JvmStatic
        val history = HistoryFragment.newInstance()
        @JvmStatic
        val list = EquipmentListFragment.newInstance()
        @JvmStatic lateinit var manager: FragmentManager
    }


//    @BindView(R.id.signIn) lateinit var mSignIn:MenuItem
//    @BindView(R.id.toolbar) lateinit var mToolbar:Toolbar

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.signin, menu)

        toolbar.setOnMenuItemClickListener {
            Toast.makeText(this,"${it.title}",Toast.LENGTH_SHORT).show()
            true
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        ButterKnife.bind(this)
        manager = supportFragmentManager
        manager.beginTransaction()
                .add(R.id.contents, list)
                .commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

//        toolbar.inflateMenu(R.menu.signin)
//        toolbar.setOnMenuItemClickListener {
////            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            item: MenuItem? ->
//            Toast.makeText(this,item?.title,Toast.LENGTH_SHORT).show()
//            true
//        }
        setSupportActionBar(toolbar)
    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null){

        }
    }

}
//
//class MainActivity : AppCompatActivity(),NetworkReceiver.OnNetworkStateListener {
//
//    override fun changeToOffline() {
////        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        if (mConSwitch.isChecked) {
//            mConSwitch.checkedChanges()
//            mConSwitch.text = OFFLINE
//            mConSwitch.invalidate()
//        }
//    }
//
//    override fun changeToMobile(info: NetworkInfo) {
////        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//
//        if (mConSwitch.isChecked) {
//            mConSwitch.checkedChanges()
//            mConSwitch.text = OFFLINE
//            mConSwitch.invalidate()
//        }
//    }
//
//    override fun changeToWifi(info: WifiInfo) {
////        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        mConSwitch.text = ONLINE
//        mConSwitch.checked()
//        mConSwitch.invalidate()
//    }
//
//    companion object {
//        private val ACCEPT_SSID = """"JMS-SOFT""""
//        private val ONLINE = "ONLINE"
//        private val OFFLINE = "OFFLINE"
//        val pageTitle = arrayOf("List", "History")
//        private val onlineInit = OnlineInitialize.newInstance()
//        private val offlineInit = OfflineInitialize.newInstance()
//        private val initializer =Initializer.newInstance()
//    }
//    @BindView(R.id.switch1) lateinit var mConSwitch:Switch
//    @BindView(R.id.tabLayout) lateinit var mTabLayout:TabLayout
//    @BindView(R.id.viewPager) lateinit var mViewPager:ViewPager
//    @BindView(R.id.fabCamera) lateinit var mCamera:FloatingActionButton
//    @BindView(R.id.input_modified) lateinit var mModifedBy:EditText
//
//    private val mReceiver = NetworkReceiver(this)
//    private val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
//
//    private lateinit var progress:ProgressDialog
//    private lateinit var networkType:String
//
//    private fun init(type:String){
//        progress.show()
//        when(type){
//            /** online initialization*/
//            ONLINE->{
//                initializer.createObservable(onlineInit.createSingle())
//                        .subscribeOn(Schedulers.computation())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                /** onSuccess */
//                                {
//                                    progress.dismiss()
//                                    mConSwitch.text = ONLINE
//                                    mConSwitch.checked()
//                                    val adapter = object : FragmentPagerAdapter(supportFragmentManager) {
//                                        override fun getItem(position: Int): Fragment {
//                                            return if(position == 0){
//                                                EquipmentListFragment.newInstance()
//                                            }else
//                                                HistoryFragment.newInstance()
//                                        }
//
//                                        override fun getPageTitle(position: Int): CharSequence? {
//                                            return pageTitle[position]
//                                        }
//
//                                        override fun getCount(): Int {
//                                            return pageTitle.count()
//                                        }
//                                    }
//                                    mViewPager.adapter =adapter
//                                    mTabLayout.setupWithViewPager(mViewPager)
//                                },
//                                /** onError */
//                                {
//                                    progress.dismiss()
//                                    mConSwitch.text= OFFLINE
//                                    LogUtil.e(it)
//                                    Toast.makeText(this,"Initialize failed",Toast.LENGTH_SHORT).show()
//                                }
//                        )
//                        .addTo(initializer.disposable)
//            }
//            /** offline initialization */
//            OFFLINE ->{
//                initializer.createObservable(offlineInit.createSingle())
//                        .subscribeOn(Schedulers.computation())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                /** onSuccess */
//                                {
//                                    progress.dismiss()
//                                    mConSwitch.text = OFFLINE
//                                    val adapter = object : FragmentPagerAdapter(supportFragmentManager) {
//                                        override fun getItem(position: Int): Fragment {
//                                            return if(position == 0){
//                                                EquipmentListFragment.newInstance()
//                                            }else
//                                                HistoryFragment.newInstance()
//                                        }
//
//                                        override fun getPageTitle(position: Int): CharSequence? {
//                                            return pageTitle[position]
//                                        }
//
//                                        override fun getCount(): Int {
//                                            return pageTitle.count()
//                                        }
//                                    }
//                                    mViewPager.adapter =adapter
//                                    mTabLayout.setupWithViewPager(mViewPager)
//                                },
//                                /** onError */
//                                {
//                                    progress.dismiss()
//                                    mConSwitch.text= OFFLINE
//                                    LogUtil.e(it)
//                                    Toast.makeText(this,"Initialize failed",Toast.LENGTH_SHORT).show()
//                                }
//                        )
//                        .addTo(initializer.disposable)
//
//            }
//        }
//
//
//    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        ButterKnife.bind(this)
//        Realm.init(this)
//
//        progress = ProgressDialog (this)
//        progress.setCancelable(false)
//
//        val info = NetworkReceiver(this).getNetworkInfo()
//
//
//        if (info is WifiInfo) {
//            if (info.ssid == ACCEPT_SSID) {
//                networkType = ONLINE
//            }else{
//                mConSwitch.text= OFFLINE
//                networkType = OFFLINE
//            }
//        }else{
//            mConSwitch.text= OFFLINE
//            networkType = OFFLINE
//        }
//
//        init(networkType)
//
//
//        mConSwitch.setOnCheckedChangeListener { _, check ->
//            if (check) {
//                mConSwitch.text = ONLINE
//            }else
//                mConSwitch.text = OFFLINE
//
//            mConSwitch.invalidate()
//        }
//        mCamera.setOnClickListener {
//            if (mModifedBy.text.length == 3) {
//                IntentIntegrator(this).initiateScan()
//            }
//        }
//    }
//
//
//    override fun onResume() {
//        super.onResume()
//        registerReceiver(mReceiver,filter)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        unregisterReceiver(mReceiver)
//    }
//
//    /**
//     *  他のアクティビティからのデータ (Intent)を受け取る
//     *  カメラから読取結果を受けとる
//     */
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
////        if (data != null) {
////            progress.show()
////            val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
////            val code = intentResult.contents.lines()[0]
////            val sjson = CreateJson.createUpdateJson(code,mModifedBy.text.toString())
////            ClientProvider.client.transaction(sjson)
////                    .subscribeOn(Schedulers.computation())
////                    .observeOn(AndroidSchedulers.mainThread())
////                    .subscribe(
////                        /** onSuccess*/
////                        {
////                            progress.dismiss()
////                            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
////                        },
////                        /** onError*/
////                        {
////                            progress.dismiss()
////                            Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
////                        }
////                    )
////        }
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        initializer.dispose()
//    }
//
//}
