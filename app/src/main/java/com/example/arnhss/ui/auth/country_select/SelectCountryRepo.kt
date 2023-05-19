package com.example.arnhss.ui.auth.country_select

import android.content.Context
import com.example.arnhss.models.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader


// Singleton
class SelectCountryRepo(private val ctx: Context) {

    private var countryData: List<Country> = listOf<Country>()


    private fun setCountryDataObjects(): List<Country> {
        val fileName = "country_code.json"
        val inputStream = ctx.assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val jsonText = reader.use { it.readText() }
        val countryListType = object : TypeToken<List<Country>>() {}.type
        return Gson().fromJson(jsonText, countryListType)
    }

    fun getCountryData(): List<Country> {
       countryData= setCountryDataObjects()
        return countryData
    }
}