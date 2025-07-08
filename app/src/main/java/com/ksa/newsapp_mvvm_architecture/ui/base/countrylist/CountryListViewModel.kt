package com.ksa.newsapp_mvvm_architecture.ui.base.countrylist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksa.newsapp_mvvm_architecture.data.model.Country
import com.ksa.newsapp_mvvm_architecture.data.repository.CountryListRepository
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CountryListViewModel (private val countryListRepository: CountryListRepository): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Country>>>(UiState.Loading)
    val uiState  : StateFlow<UiState<List<Country>>> = _uiState

    init {
        getCountryList()
    }

    private fun getCountryList(){
        viewModelScope.launch {
            countryListRepository.getCountryList().catch { error ->
                _uiState.value = UiState.Error(error.message.toString())
            }.collect{
                Log.d("getCountryList ",it.toString())
                _uiState.value = UiState.Success(it)
            }
        }
    }

}