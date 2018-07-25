package com.zhangge.chartdsl

import com.github.mikephil.charting.charts.BarChart

/**
 * Created by zhangge on 2018/6/28.
 */

fun BarChart.init(init: DSLBarChartInit.() -> Unit) {
    var config = DSLBarChartInit().apply { init() }
    config.parse(this)
}



