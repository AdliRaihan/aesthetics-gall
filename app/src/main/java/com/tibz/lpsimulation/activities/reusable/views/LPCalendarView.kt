package com.tibz.lpsimulation.activities.reusable.views

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.res.ResourcesCompat
import com.tibz.lpsimulation.R
import com.tibz.lpsimulation.databinding.CompCalendarNumberBinding
import okhttp3.internal.format
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.*
import java.util.logging.SimpleFormatter

class LPCalendarView(
    private val context: Context,
    private val delegate: WeakReference<LPCalendarViewInit>?,
    private val widthParent: Int
    ): BaseAdapter() {
    private val weekDay: Array<String> = arrayOf(
        "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
    )
    val totalCalendarDateDivided: Int
        get() {
            return totalCalendarDate / 7
        }

    private val totalRowDecreased: Int
        get() {
            return totalRow - 1
        }

    private var totalCalendarDate = 49
    private var totalDay: Int = -1
    private var currentDayFirst: String = "None"
    private var taggedDecrement = 0
    private var todayDate = 0

    private var totalRow = 7
    interface LPCalendarViewInit {
        fun createView(viewGroup: ViewGroup): View
    }
    override fun getCount(): Int {
        return totalCalendarDate
    }
    override fun getItem(p0: Int): Any {
        return ""
    }
    override fun getItemId(p0: Int): Long {
        return 0
    }
    @SuppressLint("SetTextI18n")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
        if (p2 == null) return null
        data()

        val dataView = delegate?.get()?.createView(p2) ?: return null

        CompCalendarNumberBinding.bind(dataView).apply {
            val totalDayAccumulated = (totalDay + totalCalendarDateDivided)
            val cumulatedTaggedDecrement = totalRow + taggedDecrement
            val reversedIteration = (totalRow - p0) + taggedDecrement
            if (p0 < totalRow) { // top side
                this.root.isEnabled = false
                this.number.text = weekDay[p0]
                this.number.textSize = 11f
            }
            else if (p0 < cumulatedTaggedDecrement) {
                this.root.isEnabled = false
                // for left side
                this
                    .number
                    .text = (
                        ((totalDayAccumulated + 1) - totalRow) - reversedIteration
                        ).toString()
                this.number.alpha = 0.25f
            }
            else if (totalDayAccumulated + taggedDecrement > p0) {
                val intIndex = p0 - (totalRowDecreased + taggedDecrement)
                this.number.alpha = 0.5f
                // for middle side
                this.number.text = (intIndex).toString()
                if (todayDate == intIndex) {
                    this.numberIndicator.visibility = View.VISIBLE
                    this.number.typeface = ResourcesCompat.getFont(context, R.font.open_sans_extra_bold)
                    this.number.alpha = 1f
                }
            }
            else {
                // for right side
                this.root.isEnabled = false
                this.number.alpha = 0.25f
                this.number.text = (p0 - (totalRowDecreased + taggedDecrement) - totalDay).toString()
            }
        }
        dataView.layoutParams?.width?.apply {
            dataView.layoutParams?.height = widthParent/totalCalendarDateDivided
        }
        return dataView
    }
    private fun data() {
        if (totalDay != -1) return
        val cD = Calendar.getInstance()
        cD.set(cD.get(Calendar.YEAR), cD.get(Calendar.MONTH), 1)
        val formatter = SimpleDateFormat("EEE", Locale.ENGLISH)
            .format(cD.time)
        currentDayFirst = formatter

        taggedDecrement = weekDay.indexOf(currentDayFirst)

        todayDate = Calendar.getInstance().get(Calendar.DATE)
        totalDay = cD.getActualMaximum(Calendar.DAY_OF_MONTH)

    }
}