package com.ksa.newsapp_mvvm_architecture.ui.newssources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksa.newsapp_mvvm_architecture.data.model.Source
import com.ksa.newsapp_mvvm_architecture.data.repository.NewsSourcesRepository
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NewsSourcesViewModel (private val newsSourcesRepository: NewsSourcesRepository): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Source>>>(UiState.Loading)
    val uiState : StateFlow<UiState<List<Source>>> = _uiState

    init {
        getNewsSources()
    }

    fun getNewsSources(){
        viewModelScope.launch {
            newsSourcesRepository.getNewsSources().catch {error ->
                _uiState.value = UiState.Error(error.message.toString())
            }.collect{
                _uiState.value = UiState.Success(it)
            }
        }
    }
}