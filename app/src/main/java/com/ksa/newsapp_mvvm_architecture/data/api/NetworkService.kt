package com.ksa.newsapp_mvvm_architecture.data.api

import com.ksa.newsapp_mvvm_architecture.data.model.NewsSourcesResponse
import com.ksa.newsapp_mvvm_architecture.data.model.Source
import com.ksa.newsapp_mvvm_architecture.data.model.TopHeadlinesResponse
import com.ksa.newsapp_mvvm_architecture.utils.AppConstants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country :String): TopHeadlinesResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlinesFromSelectedSource(@Query("sources") source :String): TopHeadlinesResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines/sources")
    suspend fun getNewsSources(): NewsSourcesResponse
}