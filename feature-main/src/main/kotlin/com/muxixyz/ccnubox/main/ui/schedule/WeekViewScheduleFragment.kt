package com.muxixyz.ccnubox.main.ui.schedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.muxixyz.ccnubox.home.R

class WeekViewScheduleFragment(var pos: Int) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_schedule_week_view,container,false)
        return view
    }
}