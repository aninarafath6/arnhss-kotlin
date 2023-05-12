package com.example.arnhss.activities

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.core.content.ContextCompat
import com.example.arnhss.R
import com.example.arnhss.databinding.ActivityLoginBinding
import com.example.arnhss.databinding.ActivityOnboardingBinding

class LoginActivity : AppCompatActivity() {

    private  lateinit var binding:ActivityLoginBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupAppbar()
    }

    @SuppressLint("SetTextI18n")
    private fun setupAppbar() {
        val appbar =binding.loginAppbar.commonToolbar
        val appTitle = binding.loginAppbar.titleC


        appTitle.text = "Login"
//        appTitle.foregroundGravity = Gravity.CENTER
        appbar.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
        setSupportActionBar(appbar)


    }
}