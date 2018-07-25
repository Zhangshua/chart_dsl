package com.zhangge.chartdsl.data

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.*
import com.zhangge.chartdsl.DSLBaseP
import com.zhangge.chartdsl.R

/**
 * Created by zhangge on 2018/6/30.
 */
open class DLSChartData<T:DSLDataSet<*,*>,R>(set:T):DSLBaseP<R>(){
    var dls_dataSet=dslAttrMulti(set)
}
open class DLSBarData:DLSChartData<DSLBarSet,BarData>(DSLBarSet()){
    override fun selfParse(it: Any, chart: BarData): Boolean {

        return when(it){
            is DSLBarSet -> {
                var dataSet = BarDataSet(it.dsl_dataList,"").apply {
                    it.parse(this)
                }
                chart.addDataSet(dataSet)
                true
            }
            else -> super.selfParse(it, chart)
        }
    }
}