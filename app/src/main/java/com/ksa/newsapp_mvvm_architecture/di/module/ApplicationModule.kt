package com.ksa.newsapp_mvvm_architecture.di.module

import android.app.Application
import com.ksa.newsapp_mvvm_architecture.NewsApplication
import com.ksa.newsapp_mvvm_architecture.di.ApplicationContext
import com.ksa.newsapp_mvvm_architecture.di.BaseURL
import dagger.Module
import dagger.Provides
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: NewsApplication) {
    @ApplicationContext
    @Provides
    fun provideApplication(): Application = application

    @BaseURL
    @Provides
    fun provideBaseUrl():String = "https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory
    = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(@BaseURL baseURL: String,
                              gsonConverterFactory: GsonConverterFactory){

    }
}