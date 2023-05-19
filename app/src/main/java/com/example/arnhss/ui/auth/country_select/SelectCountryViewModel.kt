package com.example.arnhss.ui.auth.country_select

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.*
import com.example.arnhss.models.Country
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SelectCountryViewModel(application: Application) : AndroidViewModel(application) {



    // initialization
    private var liveCountryData = MutableLiveData<List<Country>>()
    private var countryList: List<Country>? = listOf()
    private  val selectedCountryRepo: SelectCountryRepo = SelectCountryRepo(application)
    private var isEmpty = MutableLiveData(false)

    // custom coroutineScope
//    private val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Default)


    // initialize the selectCountryRepository


    // get the live data from mutable live data
    fun getLiveCountryData(): LiveData<List<Country>> {
        // just returning the livedata object instead the mutableLive data
        return liveCountryData
    }
    init {
        getCountryData()
    }



  private  fun getCountryData() {
        viewModelScope.launch(Dispatchers.Default) {
            countryList = selectedCountryRepo.getCountryData()
            liveCountryData.postValue(countryList)
            Log.d("ViewModel", "get country data")
        }
    }

    // search handling
    fun searchHandler(data: String) {

        val query = data.toString().lowercase()

        if (query.isEmpty()) {
            liveCountryData.value = countryList
        } else {
            val result = countryList?.filter<Country> {
                it.name.contains(query, true) || it.code.contains(
                    query, true

                ) || it.dial_code.contains(
                    query, true
                )
            }
            liveCountryData.value = result
            isEmpty.value = result?.isEmpty()
        }
    }

    fun getIErrorState(): LiveData<Boolean> {
        return isEmpty
    }

    fun setErrorState(state:Boolean){
        isEmpty.value = state
    }
}