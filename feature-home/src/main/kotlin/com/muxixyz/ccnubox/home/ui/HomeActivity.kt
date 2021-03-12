package com.muxixyz.ccnubox.home.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.muxixyz.ccnubox.home.R
import com.muxixyz.ccnubox.home.databinding.HomeActivityBinding
import com.muxixyz.ccnubox.schedule.export.IScheduledFragment
import com.muxixyz.ccnubox.timetable.export.ITimetableFragment
import com.muxixyz.ccnubox.uikit.base.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    var mCurrentFragmentTag: String? = "schedule"
    val model: HomeViewModel by viewModel()
    lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<HomeActivityBinding>(this, R.layout.home_activity)
//        binding.loadingView
        model.imageListLD.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                Toast.makeText(
                    this@HomeActivity,
                    "Error when loading Carousel.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
//            adapter.refresh(it)
            }
        })

        bottomNavView = findViewById(R.id.home_bottom_nav)
        bottomNavView.setOnNavigationItemSelectedListener(this)

        initView()


        model.refreshTodos()
    }

    private fun initView() {
        showFragment("schedule")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_schedule -> showFragment("schedule")
            R.id.action_timetable -> showFragment("timetable")
            R.id.action_tools -> showFragment("tools")
            R.id.action_profile -> showFragment("profile")
        }
        return true
    }

    private fun showFragment(tag: String) {
        val fm = supportFragmentManager
        val targetFragment = fm.findFragmentByTag(tag)
        val mCurrentFragment = fm.findFragmentByTag(mCurrentFragmentTag)

        val fragmentTransaction = fm.beginTransaction()

        if (targetFragment != null) {
            fragmentTransaction.hide(targetFragment)
        }
        if (mCurrentFragment != null) {
            mCurrentFragmentTag = tag
            fragmentTransaction.show(mCurrentFragment)
            fragmentTransaction.commitNow()
            return
        }

        showFragment(tag, fragmentTransaction)
    }

    private fun showFragment(tag: String, fragmentTransaction: FragmentTransaction) {
        val scheduleFragment by inject<IScheduledFragment>()
        val timetableFragment by inject<ITimetableFragment>()

        // todo koin DI fragment
        val fragment: Fragment = when (tag) {
            "schedule" -> scheduleFragment
//            "timetable" ->
//            "tools" ->
//            "profile" ->
            else -> timetableFragment
        }
        fragmentTransaction.add(R.id.home_content, fragment, tag)
        mCurrentFragmentTag = tag
        fragmentTransaction.commitNow()
    }

}