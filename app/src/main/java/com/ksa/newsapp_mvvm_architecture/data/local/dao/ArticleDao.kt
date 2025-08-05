package com.ksa.newsapp_mvvm_architecture.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ksa.newsapp_mvvm_architecture.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getAll(): Flow<List<ArticleEntity>>

    @Insert
    fun insertAll(articles: List<ArticleEntity>)

    @Query("DELETE FROM article")
    fun deleteAll()

    @Transaction
    fun deleteAllAndInsertAll(articles: List<ArticleEntity>) {
        deleteAll()
        return insertAll(articles)
    }
}