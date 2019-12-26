package com.nandaadisaputra.projectakhir.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.nandaadisaputra.projectakhir.R
import com.nandaadisaputra.projectakhir.ui.activity.login.RegisterActivity
import org.jetbrains.anko.startActivity


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)
        val splashInterval = 3000
        Handler().postDelayed(object : Runnable {
            override fun run() {
                startActivity<RegisterActivity>()
                this.finish()
            }

            private fun finish() {}
        }, splashInterval.toLong())
    }
}