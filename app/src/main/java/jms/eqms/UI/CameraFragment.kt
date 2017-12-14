package jms.eqms.UI

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.google.zxing.integration.android.IntentIntegrator
import jms.eqms.Sample.SampleActivity

/**
 * Created by jmsbusinesssoftmac on 2017/12/14.
 */
class CameraFragment:Fragment(),SampleActivity.TabListener{
    interface CameraListener{
        fun onFinished()
    }

    override fun onCameraActivate() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        IntentIntegrator.forSupportFragment(this).initiateScan()
    }


    companion object {
        @JvmStatic fun newInstance():Fragment = CameraFragment()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {

        }
        if (activity is SampleActivity) {
            val listener = activity as CameraListener
        }
    }
}