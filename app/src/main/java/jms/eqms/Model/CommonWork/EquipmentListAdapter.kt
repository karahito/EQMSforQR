package jms.eqms.Model.CommonWork

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter
import jms.eqms.Entity.MobileEquipmentEntity
import jms.eqms.R
import java.text.SimpleDateFormat


/**
 * @author D.Noguchi
 */
internal class EquipmentListAdapter(context: Context, realmResults: OrderedRealmCollection<MobileEquipmentEntity>): RealmBaseAdapter<MobileEquipmentEntity>(realmResults), ListAdapter {
    companion object {

    }
    /** resのLayoutResourceを参照するためにLayoutInflaterを生成*/
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    /**
     * ButterKnifeでViewFiledを定義
     * ＊この時点ではBindされていないので参照値はnull
     */
    @BindView(R.id.row_code) lateinit var mCode:TextView
    @BindView(R.id.row_worker) lateinit var mWorker:TextView
    @BindView(R.id.row_date) lateinit var mDate:TextView

    @SuppressLint("InflateParams", "SimpleDateFormat")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /**
         * Viewを取得する
         * convertViewがNullの場合は参照するViewを定義する
         */
        val view = convertView ?: inflater.inflate(R.layout.equipment_list_row,null)

        /** ButterKnifeで定義したViewFieldをbindする*/
        ButterKnife.bind(this,view)

        adapterData?.get(position)?.run {
            mCode.text = code
            mWorker.text = worker
            mDate.text = latest_update
        }


//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return view
    }
}