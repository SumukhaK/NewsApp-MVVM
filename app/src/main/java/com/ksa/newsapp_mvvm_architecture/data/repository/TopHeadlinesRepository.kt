package com.ksa.newsapp_mvvm_architecture.data.repository

import com.ksa.newsapp_mvvm_architecture.data.api.NetworkService
import com.ksa.newsapp_mvvm_architecture.data.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlinesRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlines(country: String): Flow<List<Article>> {

        return flow { emit(networkService.getTopHeadlines(country))
        }.map {
            it.articles
        }
    }
}