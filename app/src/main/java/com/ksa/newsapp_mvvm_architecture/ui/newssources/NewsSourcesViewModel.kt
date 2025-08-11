package com.ksa.newsapp_mvvm_architecture.ui.newssources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksa.newsapp_mvvm_architecture.data.model.Source
import com.ksa.newsapp_mvvm_architecture.data.repository.NewsSourcesRepository
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import com.ksa.newsapp_mvvm_architecture.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsSourcesViewModel @Inject constructor(
    private val newsSourcesRepository: NewsSourcesRepository,
    private val dispatcherProvider: DispatcherProvider): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Source>>>(UiState.Loading)
    val uiState : StateFlow<UiState<List<Source>>> = _uiState

    init {
        getNewsSources()
    }

    fun getNewsSources(){
        viewModelScope.launch(dispatcherProvider.main) {
            newsSourcesRepository.getNewsSources()
                .flowOn(dispatcherProvider.io)
                .catch {error ->
                _uiState.value = UiState.Error(error.message.toString())
            }.collect{
                _uiState.value = UiState.Success(it)
            }
        }
    }
}