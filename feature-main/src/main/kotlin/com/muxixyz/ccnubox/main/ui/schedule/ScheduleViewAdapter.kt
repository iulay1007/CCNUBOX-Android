package com.muxixyz.ccnubox.main.ui.schedule

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

open class ScheduleViewAdapter(fragment: Fragment, var mid: Int = 0) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }

    open fun leftMove() {

    }

    open fun rightMove() {

    }

}