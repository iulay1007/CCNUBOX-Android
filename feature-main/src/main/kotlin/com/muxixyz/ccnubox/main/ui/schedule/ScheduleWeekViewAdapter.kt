package com.muxixyz.ccnubox.main.ui.schedule

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

class ScheduleWeekViewAdapter(fragment: Fragment, mid : Int = 0) : ScheduleViewAdapter(fragment, mid) {
    override fun createFragment(position: Int): Fragment {
        val fragment = WeekViewScheduleFragment(position)
        Log.d("new fragment",""+ position)
        return fragment
    }

    override fun leftMove() {
        super.leftMove()
    }

    override fun rightMove() {
        super.rightMove()
    }
}