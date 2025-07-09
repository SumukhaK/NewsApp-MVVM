package com.ksa.newsapp_mvvm_architecture.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksa.newsapp_mvvm_architecture.data.model.Article
import com.ksa.newsapp_mvvm_architecture.data.repository.TopHeadlinesRepository
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TopHeadlinesViewModel(private val topHeadlinesRepository: TopHeadlinesRepository): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    fun fetchNews(country: String = ""){
        var countryParam :String = "us"
        if(country.isNotBlank()){
            countryParam = country
        }
        viewModelScope.launch{
            topHeadlinesRepository.getTopHeadlines(country = countryParam)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect{
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun fetchNewsFromSource(source: String){
        if(!source.isNullOrBlank()){
            viewModelScope.launch{
                topHeadlinesRepository.getHeadlinesFromSelectedSource(source = source)
                    .catch { e ->
                        _uiState.value = UiState.Error(e.toString())
                    }.collect{
                        _uiState.value = UiState.Success(it)
                    }
            }
        }
    }
}