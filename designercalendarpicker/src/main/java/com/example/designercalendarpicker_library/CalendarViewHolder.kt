package com.example.designercalendarpicker_library

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_calendar_day_view.view.*
import kotlinx.android.synthetic.main.item_calendar_month_view.view.*

internal abstract class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(item: CalendarEntity, actionListener: (CalendarEntity, Int) -> Unit)
}

internal class MonthViewHolder(private val view: View) : CalendarViewHolder(view) {
    private val name by lazy { view.vMonthName }

    override fun onBind(item: CalendarEntity, actionListener: (CalendarEntity, Int) -> Unit) {
        if (item is CalendarEntity.Month) {
            name.text = item.label
        }
    }
}

internal open class WeekViewHolder(view: View) : CalendarViewHolder(view) {
    override fun onBind(item: CalendarEntity, actionListener: (CalendarEntity, Int) -> Unit) {
    }
}

internal class DayViewHolder(view: View) : CalendarViewHolder(view) {
    private val name by lazy { view.vDayName }
    private val halfLeftBg by lazy { view.vHalfLeftBg }
    private val halfRightBg by lazy { view.vHalfRightBg }

    override fun onBind(item: CalendarEntity, actionListener: (CalendarEntity, Int) -> Unit) {
        if (item is CalendarEntity.Day) {
            name.text = item.label
            when (item.selection) {
                SelectionType.START -> {
                    name.select()
                    halfLeftBg.dehighlight()
                    if (item.isRange) halfRightBg.highlight()
                    else halfRightBg.dehighlight()
                }
                SelectionType.END -> {
                    name.select()
                    halfLeftBg.highlight()
                    halfRightBg.dehighlight()
                }
                SelectionType.BETWEEN -> {
                    name.deselect()
                    halfRightBg.highlight()
                    halfLeftBg.highlight()
                    name.setTextColor(getFontColor(item))
                }
                SelectionType.NONE -> {
                    halfLeftBg.dehighlight()
                    halfRightBg.dehighlight()
                    name.deselect()
                }
            }

            name.setTextColor(getFontColor(item))
            if (item.state != DateState.DISABLED) {
                itemView.setOnClickListener {
                    actionListener.invoke(
                        item,
                        adapterPosition
                    )
                }
            } else {
                itemView.setOnClickListener(null)
            }
        }
    }

    private fun getFontColor(item: CalendarEntity.Day): Int {
        return if (item.selection == SelectionType.START || item.selection == SelectionType.END) {
            ContextCompat.getColor(itemView.context, R.color.calendar_day_selected_font)
        } else if (item.selection == SelectionType.BETWEEN) {
            ContextCompat.getColor(itemView.context, R.color.calendar_picker_between_day)
        } else {
            val color = when (item.state) {
                DateState.DISABLED -> R.color.colorEventCalendar
//                DateState.WEEKEND -> R.color.calendar_day_weekend_font // todo если пондобится
                else -> R.color.colorTextMain
            }
            ContextCompat.getColor(itemView.context, color)
        }
    }

    private fun View.select() {
        val drawable = ContextCompat.getDrawable(context, R.drawable.background_selected_day_in_calendar)
        background = drawable
    }

    private fun View.deselect() {
        background = null
    }

    private fun View.dehighlight() {
        val color = ContextCompat.getColor(context, R.color.calendar_day_unselected_bg)
        setBackgroundColor(color)
    }

    private fun View.highlight() {
        val color = ContextCompat.getColor(context, R.color.calendar_day_range_selected_bg)
        setBackgroundColor(color)
    }
}

internal class EmptyViewHolder(view: View) : WeekViewHolder(view)