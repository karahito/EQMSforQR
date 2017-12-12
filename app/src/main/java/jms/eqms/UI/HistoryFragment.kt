package jms.eqms.UI

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import butterknife.BindView
import butterknife.ButterKnife
import io.realm.Realm
import io.realm.RealmResults
import jms.android.common.LogUtil
import jms.eqms.Entity.HistoryEntity
import jms.eqms.Model.CommonWork.HistoryListAdapter
import jms.eqms.R

/**
 * Created by jmsbusinesssoftmac on 2017/12/12.
 */
class HistoryFragment:Fragment() {
    companion object {
        @JvmStatic fun newInstance():Fragment=HistoryFragment()
    }

    @BindView(R.id.historyList) lateinit var mHistoryList:ListView

    private lateinit var realm:Realm
    private lateinit var rResult:RealmResults<HistoryEntity>
    private lateinit var adapter: HistoryListAdapter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        LogUtil.d("DB:Open")
        realm = Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rResult = realm.where(HistoryEntity::class.java).findAllSorted("update_date")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.history_list, container, false)
        ButterKnife.bind(this,view)
        adapter = HistoryListAdapter(context!!,rResult)
        mHistoryList.adapter = adapter
        return view
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    override fun onDetach() {
        super.onDetach()
        LogUtil.d("DB:Close")
        realm.close()
    }
}