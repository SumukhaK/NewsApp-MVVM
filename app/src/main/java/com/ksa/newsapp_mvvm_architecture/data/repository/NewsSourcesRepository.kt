package com.ksa.newsapp_mvvm_architecture.data.repository

import android.util.Log
import com.ksa.newsapp_mvvm_architecture.data.api.NetworkService
import com.ksa.newsapp_mvvm_architecture.data.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsSourcesRepository @Inject constructor(private val networkService: NetworkService)  {

    fun getNewsSources(): Flow<List<Source>>{
        return  flow {
            emit(networkService.getNewsSources())
        }.map {
            Log.d("Flowed", it.toString())
            it.sources
        }
    }
}