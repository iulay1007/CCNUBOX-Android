package com.muxixyz.ccnubox.main.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.muxixyz.ccnubox.home.R
import com.muxixyz.ccnubox.home.databinding.ActivityMainBinding
import com.muxixyz.ccnubox.main.ui.home.HomeFragment
import com.muxixyz.ccnubox.main.ui.schedule.ScheduleFragment
import com.muxixyz.ccnubox.main.ui.todo.TodoFragment
import com.muxixyz.ccnubox.profile.export.IProfileFragment
import com.muxixyz.ccnubox.uikit.base.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    var mCurrentFragmentTag: String? = null
    val model: MainViewModel by viewModel()
    lateinit var bottomNavView: BottomNavigationView

    companion object {
        const val TAG_HOME = "home"
        const val TAG_SCHEDULE = "schedule"
        const val TAG_TODO = "todo"
        const val TAG_PROFILE = "profile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .apply {
                    lifecycleOwner = this@MainActivity
                }
        binding.loadingView

        bottomNavView = findViewById(R.id.home_bottom_nav)
        bottomNavView.setOnNavigationItemSelectedListener(this)

        initView()
    }

    private fun initView() {
        showFragment(TAG_HOME)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> showFragment(TAG_HOME)
            R.id.action_schedule -> showFragment(TAG_SCHEDULE)
            R.id.action_todo -> showFragment(TAG_TODO)
            R.id.action_profile -> showFragment(TAG_PROFILE)
        }
        return true
    }

    private fun showFragment(tag: String) {
        val fm = supportFragmentManager
        val targetFragment = fm.findFragmentByTag(tag)
        val mCurrentFragment = fm.findFragmentByTag(mCurrentFragmentTag)

        val fragmentTransaction = fm.beginTransaction()

        if (mCurrentFragment != null) {
            fragmentTransaction.hide(mCurrentFragment)
        }
        if (targetFragment != null) {
            mCurrentFragmentTag = tag
            fragmentTransaction.show(targetFragment)
            fragmentTransaction.commitNow()
            return
        }
        showFragment(tag, fragmentTransaction)
    }

    private fun showFragment(tag: String, fragmentTransaction: FragmentTransaction) {

        val profileFragment by inject<IProfileFragment>()

        val fragment: Fragment = when (tag) {
            TAG_HOME -> HomeFragment()
            TAG_SCHEDULE -> ScheduleFragment()
            TAG_TODO -> TodoFragment()
            else -> profileFragment
        }

        fragmentTransaction.add(R.id.home_content, fragment, tag)
        mCurrentFragmentTag = tag
        fragmentTransaction.commitNow()
    }

}