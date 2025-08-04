package com.ksa.newsapp_mvvm_architecture.data.repository

import com.ksa.newsapp_mvvm_architecture.data.api.NetworkService
import com.ksa.newsapp_mvvm_architecture.data.local.DatabaseService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineArticlesRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService) {
}