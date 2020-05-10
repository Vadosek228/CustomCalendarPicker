package com.example.designercalendarpicker_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpCalendar()
    }

    private fun setUpCalendar() {
        val startDate = Calendar.getInstance(TimeZone.getDefault(), Locale("ru"))
        val endDate = Calendar.getInstance(TimeZone.getDefault(), Locale("ru"))
        startDate.add(Calendar.YEAR, -1)

        initCalenderView(startDate.time, endDate.time)

//        calendar_view.setOnRangeSelectedListener { startDate, endDate, startLabel, endLabel ->
//            //            departure_date.text = startLabel
////            return_date.text = endLabel
//            piker_actions_container.visibility = View.VISIBLE
//
////            val startLocal = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
////            val endLocal = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
////
////            ChartHelper.startDate =
////                LocalDate.of(startLocal.year, startLocal.monthValue, startLocal.dayOfMonth)
////            ChartHelper.endDate =
////                LocalDate.of(endLocal.year, endLocal.monthValue, endLocal.dayOfMonth)
//        }

        piker_reset.setOnClickListener {
            initCalenderView(startDate.time, endDate.time)
            piker_actions_container.visibility = View.GONE
        }

    }

    private fun initCalenderView(startDate: Date, endDate: Date) {
        calendar_view.apply {
            showDayOfWeekTitle(false)
            setMode(CalendarPicker.SelectionMode.RANGE)
            setRangeDate(startDate, endDate)
        }
    }
}