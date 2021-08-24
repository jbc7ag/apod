package com.jbc7ag.apod

import android.content.Context
import android.util.DisplayMetrics
import java.text.SimpleDateFormat
import java.util.*


object Utils {

      fun getCurrentDate(days: Int = 0): String {

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val currentDate = sdf.format(Date())

          val c1 = Calendar.getInstance()
          c1.add(Calendar.DAY_OF_YEAR, days)
          val resultDate = c1.time
          val dateBefore = sdf.format(resultDate)

        return dateBefore
    }

    fun calculateNoOfColumns(context: Context, columnWidthDp: Float): Int {
        val displayMetrics: DisplayMetrics = context.getResources().getDisplayMetrics()
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / columnWidthDp + 0.5).toInt()
    }


}