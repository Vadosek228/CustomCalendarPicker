package com.example.designercalendarpicker_library

import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

internal class CalendarAdapter : RecyclerView.Adapter<CalendarViewHolder>() {

    private val data: MutableList<CalendarEntity> = mutableListOf()
    var onActionListener: (CalendarEntity, Int) -> Unit = { _, _ -> }

    fun setData(newData: List<CalendarEntity>) {
        val diffCallback = CalendarDiffCallback(data, newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return when (viewType) {
            CalendarType.MONTH.ordinal -> MonthViewHolder(
                parent.inflate(R.layout.item_calendar_month_view)
            )
//            CalendarType.WEEK.ordinal -> WeekViewHolder(parent.inflate(R.layout.item_calendar_week_view))
            CalendarType.DAY.ordinal -> DayViewHolder(parent.inflate(R.layout.item_calendar_day_view))
            else -> EmptyViewHolder(parent.inflate(R.layout.item_calendar_empty_view))
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.onBind(data[position], onActionListener)
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].calendarType
    }
}

fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachedToRoot: Boolean = false): View =
    from(context).inflate(layoutId, this, attachedToRoot)