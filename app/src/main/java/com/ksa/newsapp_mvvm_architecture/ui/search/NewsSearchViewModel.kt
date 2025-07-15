package com.ksa.newsapp_mvvm_architecture.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksa.newsapp_mvvm_architecture.data.model.Article
import com.ksa.newsapp_mvvm_architecture.data.repository.NewsSearchByKeywordRepository
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import com.ksa.newsapp_mvvm_architecture.utils.AppConstants.DEBOUNCE_TIMEOUT
import com.ksa.newsapp_mvvm_architecture.utils.AppConstants.MINIMUM_CHARS_FOR_SEARCH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class NewsSearchViewModel (private val newsSearchByKeywordRepository:
                           NewsSearchByKeywordRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState = _uiState

    private val queryString = MutableStateFlow("")

    init{
        searchNewsBykeyword()
    }

    fun searchNews(query : String){
        Log.d("SEARCHQRY",query)
        queryString.value = query
    }

    private fun searchNewsBykeyword(){
        viewModelScope.launch {
            queryString.debounce(DEBOUNCE_TIMEOUT)
                .filter{it ->
                    if(it.isNotEmpty() && it.length >= MINIMUM_CHARS_FOR_SEARCH){
                        return@filter true
                    } else {
                        _uiState.value = UiState.Success(emptyList())
                        return@filter false
                    }
                }.distinctUntilChanged()
                .flatMapLatest {
                    _uiState.value = UiState.Loading
                    Log.d("SEARCHQRY-flatMapLatest",it.toString())
                    return@flatMapLatest newsSearchByKeywordRepository
                        .getNewsByKeyword(it)
                        .catch {error ->
                            uiState.value = UiState.Error(error.message.toString())
                        }
                }.flowOn(Dispatchers.IO)
                .collect{
                    uiState.value = UiState.Success(it)
                }
        }
    }
}