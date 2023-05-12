package com.example.arnhss.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.arnhss.R

class splashActivity : AppCompatActivity() {
    private  val splashScreenTimeout:Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this,OnboardActivity::class.java)
            startActivity(intent)
            finish()
        },splashScreenTimeout)
    }
}