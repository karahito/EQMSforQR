package jms.eqms.Sample

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.google.zxing.integration.android.IntentIntegrator
import jms.android.common.LogUtil
import jms.android.common.utility.RestProvider
import jms.eqms.R
import jms.eqms.Service.EqmsService
import jms.eqms.UI.HistoryFragment
import jms.eqms.UI.LoginFragment

class SampleActivity : AppCompatActivity(),TabLayout.OnTabSelectedListener{
    override fun onTabUnselected(tab: TabLayout.Tab?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        LogUtil.d("onTabUnselected")
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        LogUtil.d("onTabReselected")
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        LogUtil.d("onTabSelected")
//        if (tab?.position == TabPost.CAMERA.position) {
//            if (TabPost.CAMERA.fragment is TabListener) {
//                val listener = TabPost.CAMERA.fragment as  TabListener
//                listener.onCameraActivate()
//            }
//        }
    }

    interface TabListener{
        fun onCameraActivate()
    }
    interface QRListener{
        fun onRead(eCode:Array<String>)
    }

    companion object {
        private val tabTitle = arrayOf("Stock","History")
        private val client = RestProvider.provider as EqmsService
        private val loginFragment = LoginFragment.newInstance()
    }

    enum class TabPost(val position:Int,val title:String,val fragment:Fragment){
//        STOCK(0,"Stock",EquipmentListFragment.newInstance()),
//        CAMERA(1,"Camera",CameraFragment.newInstance()),
        HISTORY(0,"History",HistoryFragment.newInstance())
    }

    private val adapter:FragmentPagerAdapter
    init {
            adapter = object : FragmentPagerAdapter(supportFragmentManager) {
                override fun getItem(position: Int): Fragment {
                    return TabPost.values().first{position == it.position}.fragment
                }

                override fun getPageTitle(position: Int): CharSequence? {
                    return TabPost.values().first{position == it.position}.title
                }

                override fun getCount(): Int {
                    return TabPost.values().count()
                }
            }
    }

    @BindView(R.id.frame_center) lateinit var mViewPage:ViewPager
    @BindView(R.id.tabLayout) lateinit var mTabLayout:TabLayout
    @BindView(R.id.fab) lateinit var mFAB:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        ButterKnife.bind(this)


        mViewPage.adapter = adapter
        mTabLayout.setupWithViewPager(mViewPage)

        mTabLayout.addOnTabSelectedListener(this)
        supportFragmentManager.beginTransaction()
                .add(R.id.frame_top, loginFragment)
                .commit()

        mFAB.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            val code:Array<String> = arrayOf(intentResult.contents.lines()[0])
            if (loginFragment is QRListener) {
                val listener = loginFragment as QRListener
                listener.onRead(code)
            }
        }else{
            if (loginFragment is QRListener) {
                val listener = loginFragment as QRListener
                listener.onRead(arrayOf("100000","200000"))
            }
        }
    }
}
