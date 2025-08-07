package com.productpurchase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.os.Handler
import android.view.WindowManager
import com.productpurchase.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var b: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashBinding.inflate(layoutInflater)
        var view = b.root
        setContentView(view)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({

            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()

        }, 2000)
    }
}