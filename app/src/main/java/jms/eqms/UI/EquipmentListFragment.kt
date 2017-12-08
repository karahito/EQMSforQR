package jms.eqms.UI

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import butterknife.BindView
import butterknife.ButterKnife
import io.realm.Realm
import io.realm.RealmQuery
import jms.eqms.Entity.MobileEquipmentEntity
import jms.eqms.Entity.UpdateStatus
import jms.eqms.Model.CommonWork.EquipmentListAdapter
import jms.eqms.R

/**
 * Created by jmsbusinesssoftmac on 2017/12/08.
 */
class EquipmentListFragment :Fragment(){

    private lateinit var adapter:EquipmentListAdapter
    private var query:RealmQuery<MobileEquipmentEntity> = Realm.getDefaultInstance().where(MobileEquipmentEntity::class.java)
    @BindView(R.id.listView) lateinit var mList:ListView
    /**
     * ViewInitialized
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.equipment_list, container, false)
        ButterKnife.bind(this,view)
        return view
    }


    override fun onResume() {
        super.onResume()

        val result = query.equalTo("update_status",UpdateStatus.YET.value).findAllSorted("code")
        adapter = EquipmentListAdapter(context,result)
        mList.adapter = adapter

    }
}