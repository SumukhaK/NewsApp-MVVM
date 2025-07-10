package com.ksa.newsapp_mvvm_architecture.ui.search

import androidx.lifecycle.ViewModel
import com.ksa.newsapp_mvvm_architecture.data.repository.NewsSearchByKeywordRepository

class NewsSearchViewModel (private val newsSearchByKeywordRepository:
                           NewsSearchByKeywordRepository) : ViewModel() {

}