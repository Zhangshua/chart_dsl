package com.zhangge.chartdsl

import android.graphics.Color
import android.graphics.Paint
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.formatter.IAxisValueFormatter

/**
 * Created by zhangge on 2018/6/28.
 */
open class DSLComponentBase<T : DSLComponentBase.DSLPosition<*>, R : DSLComponentBase.DSLLabel<*>, Chart : ComponentBase> constructor(position: T, label: R) : DSLBaseP<Chart>() {
    var dsl_enabled = nullBool()
    var dsl_position = dslAttrUniq(position)
    var label = dslAttrUniq(label)

    override fun parse(componentBase: Chart) {
        super.parse(componentBase)
        dsl_enabled?.let { componentBase.isEnabled = it }

    }

    open class DSLPosition<R : ComponentBase> : DSLBaseP<R>() {
        var xOffset = nullFLoat()
        var yOffset = nullFLoat()

        override fun parse(componentBase: R) {
            xOffset?.let { componentBase.xOffset = it }
            yOffset?.let { componentBase.yOffset = it }
        }
    }

    open class DSLLabel<R : ComponentBase> : DSLBaseP<R>() {
        var size = nullFLoat()
        var color = nullStr()
        override fun parse(componentBase: R) {
            size?.let { componentBase.textSize = it }
            color?.let { componentBase.textColor = Color.parseColor(it) }
        }
    }


}

open class DSLAxisBase<T : DSLComponentBase.DSLPosition<*>, R : DSLAxisBase.DSLBaseLabel<*>> constructor(position: T, label: R) : DSLComponentBase<T, R, AxisBase>(position, label) {
    var grid = dslAttrUniq(DSLGrid())
    var axisLine = dslAttrUniq(DSLAxisLine())
    var dslValueConfig = dslAttrUniq(DSLValueConfig())
    var dslValueFormatter:IAxisValueFormatter? = null
    //Todo limit
    override fun parse(componentBase: AxisBase) {
        super.parse(componentBase)
        dslValueFormatter.alsoNull { componentBase.valueFormatter = this}
    }
    open class DSLBaseLabel<R:AxisBase> : DSLLabel<R>() {
        var count: Int? = null
        var forceShow: Boolean? = null
        var enable: Boolean? = null

        override fun parse(componentBase: R) {
            super.parse(componentBase)
            count?.let {
                if(forceShow == null){
                    componentBase.labelCount = it
                }else{
                    componentBase.setLabelCount(it, forceShow!!)
                }
            }
            enable?.let { componentBase.setDrawLabels(it) }
        }
    }

    open class DSLGrid : DSLBaseP<AxisBase>() {
        var color = nullStr()
        var width = nullFLoat()
        var enable = nullBool()

        override fun parse(componentBase: AxisBase) {
            color?.let { componentBase.gridColor = it.toColor() }
            width?.let { componentBase.gridLineWidth = it }
            enable?.let { componentBase.setDrawGridLines(it) }
        }
    }

    open class DSLAxisLine :DSLBaseP<AxisBase>() {
        var color = nullStr()
        var width= nullFLoat()
        var enable= nullBool()
        override fun parse(componentBase: AxisBase) {
            color?.let { componentBase.axisLineColor = it.toColor() }
            width?.let { componentBase.axisLineWidth = it }
            enable?.let { componentBase.setDrawAxisLine(it) }
        }
    }

    class DSLValueConfig:DSLBaseP<AxisBase>(){
        var dslGranularity = nullFLoat()
        var min = nullFLoat()
        var max = nullFLoat()
        override fun parse(componentBase: AxisBase) {
            super.parse(componentBase)
            dslGranularity.alsoNull { componentBase.granularity = this }
            min.alsoNull { componentBase.axisMinimum = this }
            max.alsoNull { componentBase.mAxisMaximum = this }
        }
    }
}

class DSLXAxis : DSLAxisBase<DSLXAxis.DSLXPosition, DSLXAxis.DSLXLabel>(DSLXPosition(), DSLXLabel()) {

    class DSLXPosition : DSLPosition<XAxis>() {
        var position: XAxis.XAxisPosition? = null

        override fun parse(componentBase: XAxis) {
            super.parse(componentBase)
            position?.let { componentBase.position = it }
        }
    }

    class DSLXLabel : DSLBaseLabel<XAxis>() {
        var angle = nullFLoat()
        var dslAvoidFirstLastClipping = nullBool()
        override fun parse(componentBase: XAxis) {
            super.parse(componentBase)
            angle?.let { componentBase.labelRotationAngle = it }
            dslAvoidFirstLastClipping.alsoNull { componentBase.setAvoidFirstLastClipping(this) }
        }
    }
}

class DSLYAxis : DSLAxisBase<DSLComponentBase.DSLPosition<YAxis>, DSLYAxis.DSLYLabel>(DSLPosition(), DSLYLabel()) {
    var zeroLine = dslAttrUniq(ZeroLine())

    class ZeroLine :DSLBaseP<YAxis>(){
        var enabled = nullBool()
        var color= nullStr()
        var width= nullFLoat()
        override fun parse(chart: YAxis) {
            enabled?.let { chart.setDrawZeroLine(it) }
            color?.let { chart.zeroLineColor = it.toColor() }
            width?.let { chart.zeroLineWidth = it }
        }
    }

    class DSLYLabel : DSLBaseLabel<YAxis>() {
        var top= nullBool()
        var bottom = nullBool()
        var position: YAxis.YAxisLabelPosition? = null
        var dslInverted = nullBool()
        override fun parse(chart: YAxis) {
            super.parse(chart)
            position?.let { chart.setPosition(it) }
            top?.let { chart.setDrawTopYLabelEntry(it) }
//            bottom.alsoNull { chart.setDrawBottomYLabelEntry(this) }
            dslInverted.alsoNull { chart.isInverted = this }
        }
    }
}

open class DSLLegend : DSLComponentBase<DSLComponentBase.DSLPosition<Legend>, DSLComponentBase.DSLLabel<Legend>,Legend>(DSLComponentBase.DSLPosition(), DSLComponentBase.DSLLabel()) {
    var count: Int? = null

}

open class DSLDescription:DSLComponentBase<DSLDescription.DSLDescriptionPosition,DSLDescription.DSLDescriptionLabel,Description>(DSLDescriptionPosition(),DSLDescriptionLabel()){

    class DSLDescriptionLabel: DSLComponentBase.DSLLabel<Description>(){
        var dslText = nullStr()
        override fun parse(componentBase: Description) {
            super.parse(componentBase)
            dslText.alsoNull { componentBase.text = this }
        }
    }
    class DSLDescriptionPosition:DSLPosition<Description>(){
        var  dslX = nullFLoat()
        var  dslY = nullFLoat()
        var dslTextAlign: Paint.Align? = null
        override fun parse(componentBase: Description) {
            super.parse(componentBase)
            if(dslX !=null ||dslY !=null){
                componentBase.setPosition(dslX?:0f,dslY?:0f)
            }
            dslTextAlign.alsoNull { componentBase.textAlign = this }
        }
    }

}

open class DSLLimitLine{

}
open class DSLMarker{

}
