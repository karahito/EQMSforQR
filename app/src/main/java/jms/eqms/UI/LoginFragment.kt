package jms.eqms.UI

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import jms.android.common.LogUtil
import jms.android.common.utility.RestProvider
import jms.eqms.Entity.HistoryEntity
import jms.eqms.Entity.StockTake
import jms.eqms.R
import jms.eqms.Sample.NameEntity
import jms.eqms.Sample.SampleActivity.QRListener
import jms.eqms.Service.EqmsService
import java.lang.NullPointerException


/**
 *
 */
class LoginFragment:RxFragment(), QRListener{
    override fun onRead(eCode: Array<String>) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val progress = ProgressDialog(context)
        progress.setCancelable(false)
        progress.show()
        val stockTake = StockTake(mLoginId.text.toString(),eCode)
//        val json = Gson().toJson(stockTake)
        client.transaction(stockTake.WCODE,stockTake.ECODE)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        /** */
                        {
                            try {
                                val parse:Stock? = Gson().fromJson(it.json,Stock::class.java)
                                if (parse != null) {
                                    Toast.makeText(context,"${parse.error}",Toast.LENGTH_SHORT).show()
                                }else {
                                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                                    val realm = Realm.getDefaultInstance()
                                }
                                progress.dismiss()
                            }catch (e:Exception){
                                progress.dismiss()
                                LogUtil.e(e)
                            }
                        },
                        /** */
                        {
                            LogUtil.e(it)
                            progress.dismiss()
                        }
                )
                .addTo(compositeDisposable)
    }


    companion object {
        @JvmStatic
        fun newInstance(): Fragment = LoginFragment()
        private val ID_LENGTH = 3
        private var client:EqmsService = RestProvider.provider as EqmsService
        private val compositeDisposable = CompositeDisposable()
    }

    @BindView(R.id.login_id) lateinit var mLoginId:EditText
    @BindView(R.id.login_name) lateinit var mLoginName:TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.login,container,false)
        ButterKnife.bind(this,view)

        val str = """{"ERROR":"10000 は、棚卸できませんでした（備品登録されていません）。 20000 は、棚卸できませんでした（備品登録されていません）。","ECODE":["10000","20000"]}"""
        val res = Gson().fromJson(str,Stock::class.java)
        println(res)
        /***/
        mLoginId.setOnEditorActionListener { textView, action, _ ->
            if (action == EditorInfo.IME_ACTION_DONE) setUserName(textView)
            false
        }

        return view
    }

    private fun setUserName(view: TextView){
        if(view.text.length == ID_LENGTH) {
            client.login(mLoginId.text.toString())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            /** onSuccess*/
                            {
                                try {
                                    val parse = Gson().fromJson(it.json, NameEntity::class.java)
                                    if (!parse.error.isNullOrEmpty())
                                        Toast.makeText(context,parse.error,Toast.LENGTH_SHORT).show()
                                    mLoginName.text = parse.name
                                    mLoginName.invalidate()
                                }catch (e:NullPointerException){
                                    mLoginName.text=""
                                    LogUtil.e(e)
                                }
                            },
                            /** onError */
                            {
                                LogUtil.e(it)
                            }
                    )
                    .addTo(compositeDisposable)
        }else{
            mLoginName.text = ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }
}

data class Stock(
        @SerializedName("ERROR")
        val error:String?,
        @SerializedName("ECODE")
        val ecode:List<String>?
)
