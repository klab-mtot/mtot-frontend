package com.example.mtot.ui.calendar

import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.databinding.CalendarDayBinding
import com.example.mtot.databinding.CalendarEmptyBinding
import com.example.mtot.databinding.CalendarMonthBinding
import com.example.mtot.databinding.FragmentCalendarBinding


object Type {
    const val MONTH = 0
    const val EMPTY = 1
    const val DAY = 2
}

class CalendarAdapter(val items: ArrayList<CalendarItemInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    inner class MonthViewHolder(val binding: CalendarMonthBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item : CalendarItemInfo){
            binding.calendarMonth.text = (item.cal.get(Calendar.MONTH)+1).toString() + "월"
        }
    }
    inner class EmptyViewHolder(val binding: CalendarEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item : CalendarItemInfo){
        }
    }
    inner class DayViewHolder(val binding: CalendarDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item : CalendarItemInfo){
            binding.calendarDay.text = item.cal.get(Calendar.DATE).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            Type.MONTH -> {
                return MonthViewHolder(CalendarMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            Type.EMPTY -> {
                return EmptyViewHolder(CalendarEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else-> {
                return DayViewHolder(CalendarDayBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(items[position].viewType){
            Type.MONTH -> {
                (holder as MonthViewHolder).bind(items[position])
            }
            Type.EMPTY -> {
                (holder as EmptyViewHolder).bind(items[position])
            }
            else -> {
                (holder as DayViewHolder).bind(items[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }
}