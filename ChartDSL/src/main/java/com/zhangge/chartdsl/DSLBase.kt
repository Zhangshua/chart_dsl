package com.zhangge.chartdsl

import android.graphics.Color
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.github.mikephil.charting.components.ComponentBase

/**
 * Created by zhangge on 2018/6/28.
 */


abstract class DSLBaseP<R> {
    var key: String = ""
    internal val dslList = mutableListOf<Any>()

    fun <T : DSLBaseP<*>> dslAttrUniq(default: T, key: String = ""): (T.() -> Unit) -> Unit {
        return fun(init: T.() -> Unit) {
            findDslItem(default, key).apply {
                init()
            }
        }
    }

    fun <T : DSLBaseP<*>> dslAttrMulti(default: T, key: String = ""): (T.() -> Unit) -> Unit {
        return fun(init: T.() -> Unit) {
            with(dslList) {
                default.key = key
                this.add(default)
                default
            }.apply {
                init()
            }
        }
    }

    fun <T : DSLBaseP<*>> findDslItem(default: T, key: String): T {
        var classNme = default::class.java
        var tmp = dslList.find {
            it::class.java == classNme && (it as DSLBaseP<*>).key == key
        }
        if (tmp == null) {
            default.key = key
            dslList.add(default)
            tmp = default
        }
        return tmp as T
    }

    open fun parse(chart: R) {
        dslList.forEach {
            if (!selfParse(it, chart)) {
                (it as DSLBaseP<R>).parse(chart)
            }
        }
    }

    open fun selfParse(it: Any, chart: R): Boolean {
        return false
    }
}

fun String.toColor() = Color.parseColor(this)

fun nullBool(): Boolean? = null
fun nullStr(): String? = null
fun nullInt(): Int? = null
fun nullFLoat(): Float? = null
fun <T> T?.alsoNull(block: T.() -> Unit) = this?.also(block)


