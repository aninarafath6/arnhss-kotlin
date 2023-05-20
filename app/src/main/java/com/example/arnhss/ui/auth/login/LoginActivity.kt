package com.example.arnhss.ui.auth.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.arnhss.R
import com.example.arnhss.databinding.ActivityLoginBinding
import com.example.arnhss.models.Country
import com.example.arnhss.ui.auth.country_select.SelectCountryActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val REQUEST_CODE = 1 // Request code for the second activity
    private  var  selectedCountry:Country = Country(code = "IN", name = "India", dial_code = "+91",)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAppbar()
        binding.loginAppbar.backPress.setOnClickListener {
            finish()
        }

        binding.mobileInput.countryDisplay.setOnClickListener {
            onPressCountryCode()
        }
        updateCountryCodeUi(selectedCountry)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val selectedCountry = data?.getSerializableExtra("selectedCountry") as Country

            Toast.makeText(this, selectedCountry.name, Toast.LENGTH_LONG).show()
            this.selectedCountry = selectedCountry

            updateCountryCodeUi(selectedCountry)

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
        startActivityForResult(intent, REQUEST_CODE)

    }

    private fun updateCountryCodeUi(country: Country){
        binding.mobileInput.code.text = country.dial_code

        val imageUrl = "https://flagcdn.com/48x36/${country.code.lowercase()}.png"

        print(imageUrl)

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.india_flag)  // Placeholder image
            .into(binding.mobileInput.flag)
    }
}