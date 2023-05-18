package com.example.arnhss.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.arnhss.ui.auth.login.LoginActivity
import com.example.arnhss.databinding.ActivityOnboardingBinding

class OnboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button.setOnClickListener {
            getStarted()
        }
    }


    private fun getStarted() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}