package com.example.mtot.ui.calendar

import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mtot.databinding.FragmentCalendarBinding

class CalendarFragment : Fragment() {
    lateinit var binding: FragmentCalendarBinding
    lateinit var adapter: CalendarAdapter
    val calList = ArrayList<CalendarItemInfo>()
    var scrollPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        initCalendar()
        adapter = CalendarAdapter(calList)
        val gridLayoutManager =
            GridLayoutManager(requireContext(), 7, GridLayoutManager.VERTICAL, false)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (adapter.items[position].viewType == 0) {
                    return 7
                }
                return 1
            }
        }
        binding.rvCalendar.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val position = gridLayoutManager.findFirstVisibleItemPosition()
                val item = adapter.getItem(position)
                binding.tvYear.text = item.cal.get(Calendar.YEAR).toString() + "년"
            }
        })
        binding.rvCalendar.layoutManager = gridLayoutManager
        binding.rvCalendar.adapter = adapter
        binding.rvCalendar.scrollToPosition(scrollPosition)

        return binding.root
    }

    fun initCalendar() {
        val cal = GregorianCalendar()

        for (i in -150..50) {
            val calendar = GregorianCalendar(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + i,
                1,
                0,
                0,
                0
            )
            calList.add(
                CalendarItemInfo(calendar, 0)
            )

            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
            val max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

            for (j in 1..dayOfWeek) {
                calList.add(
                    CalendarItemInfo(
                        GregorianCalendar(
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH) + i,
                            1,
                            0,
                            0,
                            0
                        ), 1
                    )
                )
            }
            for (j in 1..max) {
                calList.add(
                    CalendarItemInfo(
                        GregorianCalendar(
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH) + i,
                            j,
                        ), 2
                    )
                )
            }
            if (i < 0) {
                scrollPosition += dayOfWeek + max + 1
            }
        }
    }


}