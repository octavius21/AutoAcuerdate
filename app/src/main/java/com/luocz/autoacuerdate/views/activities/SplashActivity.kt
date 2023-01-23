package com.luocz.autoacuerdate.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.luocz.autoacuerdate.R
import com.luocz.autoacuerdate.databinding.ActivitySplashBinding
import java.lang.Thread.sleep

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screenSplash = installSplashScreen()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
 sleep(3000)
        screenSplash.setKeepOnScreenCondition { true }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}