package jms.eqms.Entity

import pl.droidsonroids.jspoon.annotation.Selector

/**
 * HtmlからJsonを生成するEntityクラス
 */
data class JsonFromHtml(
        @Selector("body")
        var json:String = ""
)
