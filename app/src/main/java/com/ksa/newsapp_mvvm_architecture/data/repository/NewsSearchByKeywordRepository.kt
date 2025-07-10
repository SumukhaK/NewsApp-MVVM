package com.ksa.newsapp_mvvm_architecture.data.repository

import com.ksa.newsapp_mvvm_architecture.data.api.NetworkService
import com.ksa.newsapp_mvvm_architecture.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsSearchByKeywordRepository @Inject constructor(private val networkService: NetworkService) {

    fun getNewsByKeyword(query: String): Flow<List<Article>> {
        return flow {
            emit(networkService.getNewsFromSearch(query))
        }.map {
            it.articles
        }
    }
}