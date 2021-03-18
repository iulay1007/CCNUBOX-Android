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

    var mCurrentFragmentTag: String? = "schedule"
    val model: MainViewModel by viewModel()
    lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
//        binding.loadingView
        model.imageListLD.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                Toast.makeText(
                    this@MainActivity,
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


        model.refreshSchedules()
    }

    private fun initView() {
        showFragment("schedule")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_home -> showFragment("home")
            R.id.action_schedule -> showFragment("schedule")
            R.id.action_todo-> showFragment("todo")
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

        val scheduleFragment: ScheduleFragment by inject()
        val homeFragment: HomeFragment by inject()
        val todoFragment: TodoFragment by inject()
        val profileFragment by inject<IProfileFragment>()

        // todo koin DI fragment
        val fragment: Fragment = when (tag) {
            "schedule" -> scheduleFragment
            "home" -> homeFragment
            "todo" -> todoFragment
            "profile" -> profileFragment
            else -> homeFragment
        }
        fragmentTransaction.remove(fragment).commit()
        fragmentTransaction.add(R.id.home_content, fragment, tag)
        mCurrentFragmentTag = tag
        fragmentTransaction.commitNow()
    }

}