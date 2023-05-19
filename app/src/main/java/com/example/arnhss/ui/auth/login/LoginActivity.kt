package com.example.arnhss.ui.auth.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.arnhss.R
import com.example.arnhss.databinding.ActivityLoginBinding
import com.example.arnhss.ui.auth.country_select.SelectCountryActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupAppbar()
        binding.loginAppbar.backPress.setOnClickListener {
            finish()
        }

        binding.mobileInput.countryDisplay.setOnClickListener {
            onPressCountryCode()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupAppbar() {
        val appbar = binding.loginAppbar.commonToolbar
        val appTitle = binding.loginAppbar.titleC


        appTitle.text = "Login"
        appbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(appbar)


    }

    private fun onPressCountryCode() {
        val intent = Intent(this, SelectCountryActivity::class.java)
        startActivity(intent)

    }
}