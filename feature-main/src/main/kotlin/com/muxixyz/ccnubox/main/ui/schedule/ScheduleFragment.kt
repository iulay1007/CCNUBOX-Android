package com.muxixyz.ccnubox.main.ui.schedule

import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.muxixyz.ccnubox.home.R
import com.muxixyz.ccnubox.home.databinding.FragmentScheduleBinding
import org.koin.android.ext.android.inject

class ScheduleFragment : Fragment() {

    lateinit var dataBinding: FragmentScheduleBinding
    lateinit var mAdapter: ScheduleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mAdapter = ScheduleWeekViewAdapter(this)
        dataBinding =
            FragmentScheduleBinding.inflate(
                layoutInflater, container, false
            ).apply {
                viewType = 1
                adapter = mAdapter
            }
        dataBinding.tvScheduleChange.setOnClickListener {
            showPopupWindow()
        }

        dataBinding.ibScheduleQr.setOnClickListener {
            showSettingPopupWindow()
        }
        //mAdapter = DayViewAdapter(this)

        dataBinding.vpScheduleContent.registerOnPageChangeCallback(
            ScheduleOnPagerChangeCallback(
                dataBinding.vpScheduleContent,
                        mAdapter
            )
        )

        dataBinding.tvScheduleTitle.setText("未导入课表")
        return dataBinding.root
    }


    class ScheduleOnPagerChangeCallback(val vp: ViewPager2, val adapter: ScheduleViewAdapter) :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
//            when (position) {
//                0 -> {
//                    adapter.leftMove()
//                    vp.setCurrentItem(1, false)
//                }
//                2 -> {
//                    adapter.rightMove()
//                    vp.setCurrentItem(1, false)
//                }
//            }
        }
    }

    val scheduleViewModel: ScheduleViewModel by inject()

    interface Callback {
        fun chooseWeek()
    }

    private fun showPopupWindow() {
        //加载布局
        val layout = layoutInflater.inflate(R.layout.fragment_schedule_choose_view, null)
        //找到布局的控件
        // 实例化popupWindow
        val popupWindow = PopupWindow(
            layout,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        //控制键盘是否可以获得焦点
        popupWindow.isFocusable = true
        //监听
        val day = layout.findViewById<TextView>(R.id.tv_schedule_select_view_day)
        val week = layout.findViewById<TextView>(R.id.tv_schedule_select_view_week)
        val month = layout.findViewById<TextView>(R.id.tv_schedule_select_view_month)

        day.setOnClickListener {
            Toast.makeText(
                context,
                "${dataBinding.flScheduleContentMask.visibility}",
                Toast.LENGTH_SHORT
            ).show()
            popupWindow.dismiss()
            Toast.makeText(context, "日视图", Toast.LENGTH_LONG).show()
        }
        week.setOnClickListener {
            Toast.makeText(
                context,
                "${dataBinding.flScheduleContentMask.visibility}",
                Toast.LENGTH_SHORT
            ).show()
            popupWindow.dismiss()
            Toast.makeText(context, "周视图", Toast.LENGTH_LONG).show()
        }
        month.setOnClickListener {
            Toast.makeText(
                context,
                "${dataBinding.flScheduleContentMask.visibility}",
                Toast.LENGTH_SHORT
            ).show()
            popupWindow.dismiss()
            Toast.makeText(context, "月视图", Toast.LENGTH_LONG).show()
        }
        // Set an elevation for the popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }

        //If API level 23 or higher then execute the code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Create a new slide animation for popup window enter transition
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow.enterTransition = slideIn

            // Slide animation for popup window exit transition
            val slideOut = Slide()
            slideOut.slideEdge = Gravity.TOP
            popupWindow.exitTransition = slideOut
        }

        dataBinding.flScheduleContentMask.visibility = View.VISIBLE
        popupWindow.showAsDropDown(dataBinding.clScheduleTb, 0, 0, Gravity.START)
        popupWindow.setOnDismissListener {
            dataBinding.flScheduleContentMask.visibility = View.GONE
        }
    }

    private fun showSettingPopupWindow() {
        //加载布局
        val layout = layoutInflater.inflate(R.layout.fragment_schedule_setting, null)
        //找到布局的控件
        // 实例化popupWindow
        val popupWindow = PopupWindow(
            layout,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        //控制键盘是否可以获得焦点
        popupWindow.isFocusable = true
        //监听
        val importLl = layout.findViewById<LinearLayout>(R.id.ll_schedule_setting_import)
        val addLl = layout.findViewById<LinearLayout>(R.id.ll_schedule_setting_add)
        val exportLl = layout.findViewById<LinearLayout>(R.id.ll_schedule_setting_export)
        val termCl = layout.findViewById<ConstraintLayout>(R.id.cl_schedule_setting_term)
        val courseCl = layout.findViewById<ConstraintLayout>(R.id.cl_schedule_setting_course)
        val courseSc = layout.findViewById<SwitchCompat>(R.id.sc_schedule_setting_course_only)
        val timeSc = layout.findViewById<SwitchCompat>(R.id.sc_schedule_setting_time_display)
        val bgCl = layout.findViewById<ConstraintLayout>(R.id.cl_schedule_setting_bg)

        courseSc.isChecked = true
        timeSc.isChecked = true

        importLl.setOnClickListener {
            Toast.makeText(context, "导入课表", Toast.LENGTH_SHORT).show()
        }

        addLl.setOnClickListener {
            Toast.makeText(context, "手动填课", Toast.LENGTH_SHORT).show()
        }
        exportLl.setOnClickListener {
            Toast.makeText(context, "导出课表", Toast.LENGTH_SHORT).show()
        }
        termCl.setOnClickListener {
            Toast.makeText(context, "学期设置", Toast.LENGTH_SHORT).show()
        }
        courseCl.setOnClickListener {
            Toast.makeText(context, "课程设置", Toast.LENGTH_SHORT).show()
        }
        bgCl.setOnClickListener {
            Toast.makeText(context, "背景设置", Toast.LENGTH_SHORT).show()
        }
        courseSc.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(context, "只显示课程$isChecked", Toast.LENGTH_SHORT).show()
        }
        timeSc.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(context, "时间显示$isChecked", Toast.LENGTH_SHORT).show()
        }

        // Set an elevation for the popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }

        //If API level 23 or higher then execute the code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Create a new slide animation for popup window enter transition
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow.enterTransition = slideIn

            // Slide animation for popup window exit transition
            val slideOut = Slide()
            slideOut.slideEdge = Gravity.TOP
            popupWindow.exitTransition = slideOut
        }

        dataBinding.flScheduleContentMask.visibility = View.VISIBLE
        popupWindow.showAsDropDown(dataBinding.clScheduleTb, 0, 0, Gravity.START)

        popupWindow.setOnDismissListener {
            Toast.makeText(
                context,
                "只显示课程${courseSc.isChecked}  时间显示${timeSc.isChecked}",
                Toast.LENGTH_SHORT
            ).show()
            dataBinding.flScheduleContentMask.visibility = View.GONE
        }
    }

}