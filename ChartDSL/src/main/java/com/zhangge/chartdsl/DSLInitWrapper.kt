package com.zhangge.chartdsl

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.data.BarData
import com.zhangge.chartdsl.data.DLSBarData
import com.zhangge.chartdsl.data.DLSChartData

/**
 * Created by zhangge on 2018/6/28.
 */
class DSLBarChartInit: DSLBarLineChartInit<BarChart,DLSBarData>(DLSBarData()) {

}


open class DSLBarLineChartInit<R:BarLineChartBase<*>,T:DLSChartData<*,*>>(data:T): DSLChartInit<R,T>(data){
    var xAxis = dslAttrUniq(DSLXAxis())
    var yAxis = dslAttrUniq(DSLYAxis())
    var yAxisRight = dslAttrUniq(DSLYAxis(),"right")

    override fun selfParse(it: Any, chart: R): Boolean {
       return when(it){
            is DSLXAxis ->{
                it.parse(chart.xAxis)
                true
            }
            is DSLYAxis -> {
                when(it.key){
                    "right"-> it.parse(chart.axisRight)
                    else -> it.parse(chart.axisLeft)
                }
                true
            }
            else -> super.selfParse(it, chart)
        }
    }
}
open class DSLChartInit<T:Chart<*>,R:DLSChartData<*,*>>(data:R) :DSLBaseP<T>(){
    var dsl_dataChart = dslAttrUniq(data)

    override fun selfParse(it: Any, chart: T): Boolean {
       return when(it){
            is DLSBarData -> {
                var data = BarData().apply {
                    it.parse(BarData@this)
                }
                chart.data = data
                true
            }
            else -> super.selfParse(it, chart)
        }
    }
}

