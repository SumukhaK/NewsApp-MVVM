package com.ksa.newsapp_mvvm_architecture.data.local

import com.ksa.newsapp_mvvm_architecture.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

class AppDatabaseService constructor(private val appDatabase: AppDatabase) : DatabaseService {
    override fun getArticles(): Flow<List<ArticleEntity>> {
        return appDatabase.articleDao().getAll()
    }

    override fun deleteAllAndInsertAll(articles: List<ArticleEntity>) {
        appDatabase.articleDao().deleteAllAndInsertAll(articles)
    }

}