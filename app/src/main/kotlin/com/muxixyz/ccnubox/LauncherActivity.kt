package com.muxixyz.ccnubox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.muxixyz.ccnubox.main.ui.main.MainActivity
import net.muxi.huashiapp.R

class LauncherActivity : AppCompatActivity() {

    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launcher_activity)

        handler.postDelayed({
//            Bro.get().startActivityFrom(this@LauncherActivity)
//                .toUrl("ccnubox://home")
            startActivity(Intent(this@LauncherActivity, MainActivity::class.java))
        }, 500)
    }
}