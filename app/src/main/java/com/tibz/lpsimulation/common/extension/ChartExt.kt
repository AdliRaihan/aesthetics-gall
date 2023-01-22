package com.tibz.lpsimulation.common.extension

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.model.GradientColor
import com.tibz.lpsimulation.R
import com.tibz.lpsimulation.common.helper.SwiftHelper.Companion.lpColor
import com.tibz.lpsimulation.common.helper.SwiftHelper.Companion.lpDrawable

class ChartExt {
    companion object {
        fun LineChart.defaultStyle() {
            this.setTouchEnabled(false)
            this.legend.isEnabled = false
            this.description.isEnabled = false
            this.setDrawGridBackground(false)
            this.axisLeft.setDrawGridLines(false)
            this.axisLeft.isEnabled = false
            this.axisRight.isEnabled = false
            this.xAxis.isEnabled = false
            this.xAxis.setDrawGridLines(false)
            this.axisRight.setDrawGridLines(false)
            this.renderer.paintRender.shader = LinearGradient(
                -80f,
                80f,
                40f,
                -40f,
                context.lpColor(R.color.red),
                context.lpColor(R.color.purple_200), Shader.TileMode.CLAMP)
        }
        fun LineDataSet.defaultStyle(
            context: Context
        ) {
            this.mode = LineDataSet.Mode.CUBIC_BEZIER
            this.circleRadius = 1f
            this.setDrawFilled(true)
            this.lineWidth = 1f
            this.setGradientColor(context.lpColor(R.color.red), context.lpColor(R
                .color.purple_700))
            this.enableDashedLine(1f, 1f, 1f)
            this.fillDrawable = context.lpDrawable(R.drawable
                .b_g_chart_purple_top_bottom)
//            this.setFillFormatter { _, _ ->
//                return@setFillFormatter vBinding.moneyInvestedContainer
//                    .chartTransaction.axisLeft.axisMinimum
//            }
            this.setDrawValues(false)
            this.setDrawCircles(false)
            this.setDrawCircleHole(false)
        }
    }
}