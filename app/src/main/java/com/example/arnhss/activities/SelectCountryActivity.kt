package com.example.arnhss.activities

import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private  var liveCountryData = MutableLiveData<List<Country>>()
    private lateinit var countryList: List<Country>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySelectCountryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.circularProgress.isVisible = true


        lifecycleScope.launch(Dispatchers.Default) {

            countryList = convertJsonToDataObjects()

            liveCountryData.postValue(countryList)



        }

        binding.errorMsg.isVisible = false
        handleOnBackPressed()
        searchToggleHandler()
        onCountrySearchHandler()

        liveCountryData.observe(this){country ->
            binding.circularProgress.isVisible = false
            setupCountryList(country)

        }
    }

    fun getCountryLiveData(): LiveData<List<Country>> {
        return liveCountryData

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
                searchBox.setText("")
            } else {
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

    private fun onCountrySearchHandler() {
        binding.countrySearchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("MainActivity", s.toString())

                val query = s.toString().lowercase()

                if (query.isEmpty()) {
                    liveCountryData.value = countryList
                } else {
                    val result = countryList.filter<Country> {
                        it.name.contains(query,true) || it.code.contains(query,true) || it.dial_code.contains(
                            query,true
                        )
                    }


                    binding.errorMsg.isVisible = result.isEmpty()
                    liveCountryData.value  = result


                    Log.d("MainActivity",result.toString())



                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

}