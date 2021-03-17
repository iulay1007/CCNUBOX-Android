package com.muxixyz.ccnubox.main.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class ScheduleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(context, "ScheduleFragment.show()", Toast.LENGTH_SHORT).show()
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}