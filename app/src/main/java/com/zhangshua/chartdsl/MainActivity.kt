package com.zhangshua.chartdsl

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.zhangge.chartdsl.init
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chart.init {
            xAxis {
                dsl_position {
                    dsl_enabled = true
                    position = XAxis.XAxisPosition.BOTTOM
                    xOffset = 0f
                    yOffset = 2f
                }
                grid {
                    this.color = "#ff0000"
                    this.enable = true
                    this.width = 3F
                }
            }
            yAxisRight {
                dsl_enabled = false
            }
            dsl_dataChart {
                dls_dataSet {
                    dsl_dataList = listOf(BarEntry(1f, 2f), BarEntry(2f, 4f), BarEntry(3f, 2f), BarEntry(5f, 2f), BarEntry(7f, 2f))
                }
            }
        }


    }
}
