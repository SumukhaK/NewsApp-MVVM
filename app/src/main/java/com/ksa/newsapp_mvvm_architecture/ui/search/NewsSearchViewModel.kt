package com.ksa.newsapp_mvvm_architecture.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksa.newsapp_mvvm_architecture.data.model.Article
import com.ksa.newsapp_mvvm_architecture.data.repository.NewsSearchByKeywordRepository
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import com.ksa.newsapp_mvvm_architecture.utils.AppConstants.DEBOUNCE_TIMEOUT
import com.ksa.newsapp_mvvm_architecture.utils.AppConstants.MINIMUM_CHARS_FOR_SEARCH
import com.ksa.newsapp_mvvm_architecture.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsSearchViewModel @Inject constructor(private val newsSearchByKeywordRepository:
                                              NewsSearchByKeywordRepository,
                                              private val dispatcherProvider: DispatcherProvider) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState = _uiState

    private val queryString = MutableStateFlow("")

    init{
        searchNewsBykeyword()
    }

    fun searchNews(query : String){
        queryString.value = query
    }

    private fun searchNewsBykeyword(){
        viewModelScope.launch(dispatcherProvider.main) {
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
                    return@flatMapLatest newsSearchByKeywordRepository
                        .getNewsByKeyword(it)
                        .catch {error ->
                            uiState.value = UiState.Error(error.message.toString())
                        }
                }.flowOn(dispatcherProvider.io)
                .collect{
                    uiState.value = UiState.Success(it)
                }
        }
    }
}