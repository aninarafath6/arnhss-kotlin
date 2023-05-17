package com.example.arnhss.activities

import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.arnhss.R
import com.example.arnhss.databinding.ActivitySelectCountryBinding
import com.example.arnhss.types.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class SelectCountryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySelectCountryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.circularProgress.isVisible = true

//        onNavPop()

        lifecycleScope.launch(Dispatchers.Default) {

            convertJsonToDataObjects()


            launch(Dispatchers.Main) {
                binding.circularProgress.isVisible = false
            }

        }

        onBackPressedDispatcher.addCallback(this,onBackPressCallback )

    }


    private val onBackPressCallback = object: OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            // Check if the keyboard is open
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            var isKeyboardOpen = imm.isAcceptingText

            if (isKeyboardOpen) {
                // If the keyboard is open, close it
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                binding.countrySearchBox.clearFocus()
                } else {
                // If the keyboard is not open, finish the activity
                finish()
            }
        }

    }




    private fun convertJsonToDataObjects(): List<Country> {
        Thread.sleep(1000L)
        val fileName = "country_code.json"
        val inputStream = this.assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))


        val jsonText = reader.use { it.readText() }
        val countryListType = object : TypeToken<List<Country>>() {}.type

        return Gson().fromJson(jsonText, countryListType)
    }
//
//    private fun onNavPop(){
//
//
//
//        binding.backPress.setOnClickListener{
//            // Check if the keyboard is open
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            val isKeyboardOpen = imm.isAcceptingText
//
//            if (isKeyboardOpen) {
//                // If the keyboard is open, close it
//                imm.hideSoftInputFromWindow(binding.backPress.windowToken, 0)
//                binding.countrySearchBox.clearFocus()
//                binding.backPress.clearFocus()
//            } else {
//                // If the keyboard is not open, finish the activity or perform your desired action
//                finish()
//            }
//
//        }
//
//    }


}