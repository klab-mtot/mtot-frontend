package com.example.mtot.ui.calendar

import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mtot.databinding.ItemCalendarDayBinding
import com.example.mtot.databinding.ItemCalendarMonthBinding
import com.example.mtot.databinding.ItemCalendarEmptyBinding


class CalendarAdapter(val context: Context, val items: ArrayList<CalendarItemInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class MonthViewHolder(val binding: ItemCalendarMonthBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item : CalendarItemInfo){
            binding.calendarMonth.text = (item.cal.get(Calendar.MONTH)+1).toString() + "월"
        }
    }
    inner class EmptyViewHolder(val binding: ItemCalendarEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item : CalendarItemInfo){
        }
    }
    inner class DayViewHolder(val binding: ItemCalendarDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item : CalendarItemInfo){
            binding.calendarDay.text = item.cal.get(Calendar.DATE).toString()
            if(item.url != null)
                Glide.with(context).load(item.url).into(binding.ivCalendarDay);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            Type.MONTH -> {
                MonthViewHolder(ItemCalendarMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            Type.EMPTY -> {
                EmptyViewHolder(ItemCalendarEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
            else-> {
                DayViewHolder(ItemCalendarDayBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("hi", items[position].toString())
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

    fun getItem(position: Int): CalendarItemInfo {
        return items[position]
    }

}