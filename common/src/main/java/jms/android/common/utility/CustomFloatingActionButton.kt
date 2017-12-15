package jms.android.common.utility

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.graphics.Typeface
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.Button
import jms.android.common.LogUtil
import jms.android.common.R
import java.util.jar.Attributes


/**
 *
 */
class CustomFloatingActionButton:Button{
    constructor(context: Context):super(context){}

    constructor(context: Context,attrs:AttributeSet):super(context,attrs){
        setCustomFont(context,attrs)
    }

    private fun setCustomFont(context: Context,attrs: AttributeSet){
        val styleAttributes = context.obtainStyledAttributes(attrs,R.styleable.CustomFont)
        val customFont = styleAttributes.getString(R.styleable.CustomFont_customFont)
        setCustomFont(context,customFont)
        styleAttributes.recycle()
    }

    fun setCustomFont(context: Context,asset:String):Boolean{
        var typeface:Typeface? = null
        try{
            typeface = CachedTypefaces[context,asset]
        }catch (e:Exception){
            LogUtil.e(e)
            return false
        }

        return true
    }
}
//class CustomFontButton : Button {
//
//    constructor(context: Context) : super(context) {}
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        setCustomFont(context, attrs)
//    }
//
//    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
//        setCustomFont(context, attrs)
//    }
//
//    private fun setCustomFont(ctx: Context, attrs: AttributeSet) {
//        val a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView)
//        val customFont = a.getString(R.styleable.CustomFontTextView_customFont)
//        setCustomFont(ctx, customFont)
//        a.recycle()
//    }
//
//    fun setCustomFont(context: Context, asset: String?): Boolean {
//        var tf: Typeface? = null
//        try {
//            // ここでフォントファイル読み込み。
//            // 読み込み済みならキャッシュから。
//            tf = CachedTypefaces[context, asset!!]
//        } catch (e: Exception) {
//            Log.e(TAG, "Could not get typeface: " + e.message)
//            return false
//        }
//
//        setTypeface(tf)
//        return true
//    }
//
//    companion object {
//        private val TAG = "CustomFontButton"
//    }
//}