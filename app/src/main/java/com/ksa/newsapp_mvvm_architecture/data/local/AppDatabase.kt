package com.ksa.newsapp_mvvm_architecture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ksa.newsapp_mvvm_architecture.data.local.dao.ArticleDao
import com.ksa.newsapp_mvvm_architecture.data.local.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}