package jms.eqms.Sample

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jms.android.common.LogUtil
import jms.eqms.R
import org.jsoup.examples.HtmlToPlainText

class SampleActivity : AppCompatActivity() {

    companion object {
        @JvmStatic val client = Provider.client
    }

    @BindView(R.id.tvName) lateinit var mName:TextView
    @BindView(R.id.fabLogin) lateinit var mLogin:FloatingActionButton
    @BindView(R.id.worker_id) lateinit var mId:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        ButterKnife.bind(this)

        mLogin.setOnClickListener {

            client.read(mId.text.toString())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {

                                LogUtil.d(it.toString())
                                LogUtil.d(it.toString())

                                mName.text = it.toString()
                                mName.invalidate()
                            },
                            {
                                mName.text = it.toString()
                                mName.invalidate()
                                LogUtil.e(it)
                            }
                    )
        }

        val url = "http://192.168.1.11/inventories/?WCODE=151"
        Provider().getName(url)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            LogUtil.d(it)
                            val regX = Regex("""\{(.*)}""")
                            val match = it.toRegex().matchEntire("NAME")
                            LogUtil.d(match.toString())

                        },
                        {
                            LogUtil.e(it)
                        }
                )
//
    }

}
