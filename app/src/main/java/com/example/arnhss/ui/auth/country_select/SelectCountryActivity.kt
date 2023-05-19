package com.example.arnhss.ui.auth.country_select

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arnhss.adapters.CountryAdapter
import com.example.arnhss.databinding.ActivitySelectCountryBinding
import com.example.arnhss.models.Country

class SelectCountryActivity : AppCompatActivity(), TextWatcher {

    private lateinit var binding: ActivitySelectCountryBinding
    private lateinit var selectCountryMvvm: SelectCountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initializations
        binding = ActivitySelectCountryBinding.inflate(layoutInflater)
        selectCountryMvvm =
            ViewModelProvider(this@SelectCountryActivity)[SelectCountryViewModel::class.java]
        setContentView(binding.root)


        binding.circularProgress.isVisible = true

        Log.d("MainActivity", "${binding.countrySearchBox.text.toString()} hi from")


        // backPress callback
        val callback = object : OnBackPressedCallback(true) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            var isKeyboardOpen = imm.isActive
            val searchBox: EditText = binding.countrySearchBox

            override fun handleOnBackPressed() {

                binding.countryAppbarTitle.isVisible = true
                searchBox.isVisible = false

                if (currentFocus != null && currentFocus!!.hasFocus()) {

                    binding.countryAppbarTitle.isVisible = true
                    searchBox.isVisible = false
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

        onBackPressedDispatcher.addCallback(this, callback)
        binding.errorMsg.isVisible = false

        // handle functions
        handleOnBackPressed()
        searchToggleHandler()
        onCountrySearchHandler()


        // observe the live country data
        selectCountryMvvm.getLiveCountryData().observe(this) { country ->
            binding.circularProgress.isVisible = false
            selectCountryMvvm.setErrorState(country.isEmpty())
            setupCountryList(country)
        }
        selectCountryMvvm.getIErrorState().observe(this@SelectCountryActivity) { isEmpty ->
            binding.errorMsg.isVisible = isEmpty
        }

        binding.countrySearchBox.setText("")


    }


    // handle the screen pop function+
    private fun handleOnBackPressed() {
        // Check if the keyboard is open
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
        }
    }

    private fun onCountrySearchHandler() {
        binding.countrySearchBox.removeTextChangedListener(this)
        binding.countrySearchBox.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val response = selectCountryMvvm.searchHandler(s.toString())


    }

    override fun afterTextChanged(s: Editable?) {

    }

}