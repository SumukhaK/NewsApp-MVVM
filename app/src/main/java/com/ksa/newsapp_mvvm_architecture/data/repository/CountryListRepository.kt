package com.ksa.newsapp_mvvm_architecture.data.repository

import android.content.Context
import com.google.gson.Gson
import com.ksa.newsapp_mvvm_architecture.data.model.Country
import com.ksa.newsapp_mvvm_architecture.data.model.CountryListResponse
import com.ksa.newsapp_mvvm_architecture.di.ApplicationContext
import com.ksa.newsapp_mvvm_architecture.utils.ReadJSONFromAssets
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryListRepository @Inject constructor(@ApplicationContext private val context: Context) {

    fun getCountryList() : Flow<List<Country>> {
        val countryJsonToString = ReadJSONFromAssets(context,"countryList.json")
        val convertedData = Gson().fromJson(countryJsonToString,CountryListResponse::class.java)
        return flow {
            emit(convertedData.countries)
        }
    }
}