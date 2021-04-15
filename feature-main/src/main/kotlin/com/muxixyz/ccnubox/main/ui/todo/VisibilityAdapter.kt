package com.muxixyz.ccnubox.main.ui.todo

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
    Log.e("goneUnless", visible.toString())
}