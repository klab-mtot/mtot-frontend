package com.example.mtot.ui.calendar

import android.icu.util.GregorianCalendar
object Type {
    const val MONTH = 0
    const val EMPTY = 1
}
data class CalendarItemInfo(
    var cal : GregorianCalendar,
    var viewType : Int,
    var url : String? = null
)
