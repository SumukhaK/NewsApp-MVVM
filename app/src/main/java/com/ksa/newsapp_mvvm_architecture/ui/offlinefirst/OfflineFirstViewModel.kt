package com.ksa.newsapp_mvvm_architecture.ui.offlinefirst

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ksa.newsapp_mvvm_architecture.data.local.entity.ArticleEntity
import com.ksa.newsapp_mvvm_architecture.data.repository.OfflineArticlesRepository
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import com.ksa.newsapp_mvvm_architecture.utils.AppConstants.COUNTRY
import com.ksa.newsapp_mvvm_architecture.utils.DispatcherProvider
import com.ksa.newsapp_mvvm_architecture.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineFirstViewModel @Inject constructor(
    private val offlineArticlesRepository:
    OfflineArticlesRepository,
    private val networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ArticleEntity>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<ArticleEntity>>> = _uiState

    init {
        if (networkHelper.isNetworkConnected()) {
            fetchArticles()
        } else {
            fetchArticlesDirectlyFromDB()
        }
    }

    fun retryFetchingArticles(){
        if (networkHelper.isNetworkConnected()) {
            fetchArticles()
        } else {
            fetchArticlesDirectlyFromDB()
        }
    }

    private fun fetchArticles() {
        viewModelScope.launch {
            offlineArticlesRepository.getArticles(COUNTRY)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    private fun fetchArticlesDirectlyFromDB() {
        viewModelScope.launch {
            offlineArticlesRepository.getArticlesDirectlyFromDB()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}