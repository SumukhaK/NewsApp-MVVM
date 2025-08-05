package com.ksa.newsapp_mvvm_architecture.data.local

import com.ksa.newsapp_mvvm_architecture.data.local.entity.ArticleEntity
import com.ksa.newsapp_mvvm_architecture.data.model.Article
import kotlinx.coroutines.flow.Flow

interface DatabaseService {

    fun getArticles(): Flow<List<ArticleEntity>>

    fun deleteAllAndInsertAll(articles: List<ArticleEntity>)
}