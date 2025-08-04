package com.ksa.newsapp_mvvm_architecture.ui.offlinefirst

import androidx.lifecycle.ViewModel
import com.ksa.newsapp_mvvm_architecture.data.repository.OfflineArticlesRepository
import com.ksa.newsapp_mvvm_architecture.utils.DispatcherProvider
import javax.inject.Inject

class OfflineFirstViewModel @Inject constructor(private val offlineArticlesRepository:
                                                OfflineArticlesRepository,
    private val dispatcherProvider: DispatcherProvider): ViewModel() {
}