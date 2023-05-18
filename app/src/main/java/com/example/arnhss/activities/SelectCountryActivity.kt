package com.example.arnhss.activities

import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arnhss.R
import com.example.arnhss.adapters.CountryAdapter
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


        lifecycleScope.launch(Dispatchers.Default) {

            val countryList: List<Country> = convertJsonToDataObjects()


            launch(Dispatchers.Main) {
                binding.circularProgress.isVisible = false
                setupCountryList(countryList)
                print(countryList[0].name)

                Log.d("sample", "hi")
                Log.d("sample", "https://flagcdn.com/48x36/${countryList[0].code.lowercase()}.png")


            }

        }

        handleOnBackPressed()
        searchToggleHandler()


    }


    // handle the screen pop function+
    private fun handleOnBackPressed() {
//        // Check if the keyboard is open
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        var isKeyboardOpen = imm.isActive
        val searchBox: EditText = binding.countrySearchBox


        binding.backPress.setOnClickListener {


            if (currentFocus != null && currentFocus!!.hasFocus()) {
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                searchBox.clearFocus()
                searchBox.isVisible = false
                binding.countryAppbarTitle.isVisible = true
                isKeyboardOpen = false
            } else {
                // If the keyboard is not open, finish the activity
                finish()
            }

        }
    }

    private fun convertJsonToDataObjects(): List<Country> {
//        Thread.sleep(1000L)
        val fileName = "country_code.json"
        val inputStream = this.assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))


        val jsonText = reader.use { it.readText() }
        val countryListType = object : TypeToken<List<Country>>() {}.type

        return Gson().fromJson(jsonText, countryListType)
    }


    private fun setupCountryList(data: List<Country>) {
        val rc: RecyclerView = binding.countryRecycler
        val ll: LinearLayoutManager = LinearLayoutManager(this)

        rc.layoutManager = ll
        rc.adapter = CountryAdapter(this, data)
    }


    private fun searchToggleHandler() {

        val searchBox: EditText = binding.countrySearchBox
        val title: TextView = binding.countryAppbarTitle
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        searchBox.isVisible = false
        binding.searchIcon.setOnClickListener {
            title.isVisible = !title.isVisible

            if (!searchBox.isVisible) {
                searchBox.isVisible = true
                searchBox.requestFocus()
                imm.showSoftInput(searchBox, InputMethodManager.SHOW_IMPLICIT)

            } else {
                val isKeyboardOpen = imm.isAcceptingText
                if (isKeyboardOpen) {
                    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                    searchBox.clearFocus()
                }
                searchBox.isVisible = false
            }




            Log.d("MainActivity", binding.countryAppbarTitle.isVisible.toString())
        }
    }


}