package com.example.arnhss.ui.auth.country_select.onbaording

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arnhss.models.Country

class SelectCountryViewModel : ViewModel() {

    private var liveCountryData = MutableLiveData<List<Country>>()
    private var countryList: List<Country>? = listOf()


    // get the live data from mutable live data
    fun getLiveCountryData(): LiveData<List<Country>> {
        return liveCountryData
    }
}