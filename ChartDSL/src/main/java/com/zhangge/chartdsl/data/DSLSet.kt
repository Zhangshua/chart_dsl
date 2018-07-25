package com.zhangge.chartdsl.data

import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.utils.MPPointF
import com.zhangge.chartdsl.*

/**
 * Created by zhangge on 2018/6/30.
 */
open class DSLDataSet<R:Entry,T : DataSet<R>> : DSLBaseP<T>() {
    var dsl_dataList = emptyList<R>()
    var dsl_dataConfig = dslAttrUniq(DSLDataConfig<T>())
    var dsl_valueConfig = dslAttrUniq(DSLValueConfig<T>())
    var dsl_iconConfig = dslAttrUniq(DSLIconConfig<T>())

    open class DSLDataConfig<T : DataSet<*>> : DSLBaseP<T>() {
        var dsl_colors: List<String>? = null
        var dsl_enabled = nullBool()

        override fun parse(chart: T) {
            super.parse(chart)
            dsl_colors?.let { chart.colors = it.map { it.toColor() } }
            dsl_enabled?.let { chart.isVisible = it }
        }
    }

    open class DSLValueConfig<T : DataSet<*>> : DSLBaseP<T>() {
        var dsl_colors: List<String>? = null
        var dsl_enabled = nullBool()
        var dsl_textSize = nullFLoat()
        override fun parse(chart: T) {
            super.parse(chart)
            dsl_colors?.let { chart.setValueTextColors(it.map { it.toColor() }) }
            dsl_enabled?.let { chart.isVisible = it }
            dsl_textSize?.let { chart.valueTextSize = it }
        }
    }

    open class DSLIconConfig<T : DataSet<*>> : DSLBaseP<T>() {
        var dsl_enabled = nullBool()
        var dsl_Offset: MPPointF? = null
        override fun parse(chart: T) {
            super.parse(chart)
            dsl_Offset.alsoNull { chart.iconsOffset = this }
            dsl_enabled.alsoNull { chart.setDrawIcons(this) }
        }
    }
}
open class DSLBarSet :DSLDataSet<BarEntry,BarDataSet>(){
    var dsl_border = dslAttrUniq(DSLBarBorder())

    class DSLBarBorder :DSLBaseP<BarDataSet>(){
        var width = nullFLoat()
        var color = nullStr()
        override fun parse(chart: BarDataSet) {
            super.parse(chart)
            width.alsoNull { chart.barBorderWidth = this }
            color.alsoNull { chart.barBorderColor = this.toColor() }
        }
    }
}