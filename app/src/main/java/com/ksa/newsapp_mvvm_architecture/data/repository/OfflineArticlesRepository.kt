package com.ksa.newsapp_mvvm_architecture.data.repository

import com.ksa.newsapp_mvvm_architecture.data.api.NetworkService
import com.ksa.newsapp_mvvm_architecture.data.local.DatabaseService
import com.ksa.newsapp_mvvm_architecture.data.local.entity.ArticleEntity
import com.ksa.newsapp_mvvm_architecture.data.model.toArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineArticlesRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun getArticles(country: String): Flow<List<ArticleEntity>> {
        return flow { emit(networkService.getTopHeadlines(country)) }
            .map {
                it.articles.map { articles -> articles.toArticleEntity() }
            }.flatMapConcat { articles ->
                flow { emit(databaseService.deleteAllAndInsertAll((articles))) }
            }.flatMapConcat {
                databaseService.getArticles()
            }
    }

    fun getArticlesDirectlyFromDB(): Flow<List<ArticleEntity>> {
        return databaseService.getArticles()
    }
}