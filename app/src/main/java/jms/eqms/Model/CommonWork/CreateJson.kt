package jms.eqms.Model.CommonWork

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.JsonObject
import jms.eqms.Entity.UpdateEntity
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jmsbusinesssoftmac on 2017/12/12.
 */
object CreateJson {

    @SuppressLint("SimpleDateFormat")
    fun createUpdateJson(code: String, modified: String): UpdateEntity {
        val entity = UpdateEntity(
                code = code,
                modified_by = modified,
                update_date = SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().time)
        )
        return entity
    }
}