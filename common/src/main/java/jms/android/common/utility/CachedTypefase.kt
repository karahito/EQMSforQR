package jms.android.common.utility

import android.content.Context
import android.graphics.Typeface
import jms.android.common.LogUtil
import java.util.*


/**
 * のフォントファイル読み込みクラス
 */
object CachedTypefaces {

    /** 読み込み済みフォントファイルのキャッシュ*/
    private val cache = Hashtable<String, Typeface>()

    /**
     * フォント読み込みオペレータークラス
     * 指定されたPathのファイルが読み込み済みならキャッシュから展開する
     *
     * @param context 展開先ViewのContext
     * @param assetPath フォントファイルのパス
     */
    operator fun get(context: Context, assetPath: String): Typeface? {
        synchronized(cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    val t = Typeface.createFromAsset(context.assets, assetPath)
                    cache.put(assetPath, t)
                } catch (e: Exception) {
                    LogUtil.e("Could not get typeface $assetPath caused by:",e)
                    return null
                }
            }
            return cache[assetPath]
        }
    }

}