package jms.eqms.Model.CommonWork

import android.annotation.SuppressLint
import jms.eqms.Entity.UpdateStatus
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 */
object TakingDate{

    @SuppressLint("SimpleDateFormat")
    fun checkTakingDate(src_date:String):Boolean{
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DATE)
        if(today < 25)
            calendar.add(Calendar.MONTH,-1)

        val decision = SimpleDateFormat("yyyyMM").format(calendar.time) + "25"
        return src_date > decision

    }

    fun getUpdateStatus(src_date:String,src_status:Int): Int {
        return if (checkTakingDate(src_date)) {
            UpdateStatus.ALREADY.value
        }
        else{
            if (src_status == UpdateStatus.CALLBACK_WAIT.value) UpdateStatus.CALLBACK_WAIT.value
            else UpdateStatus.YET.value
        }
    }
}