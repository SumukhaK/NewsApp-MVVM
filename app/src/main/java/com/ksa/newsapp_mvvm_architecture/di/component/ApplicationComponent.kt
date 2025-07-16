package com.ksa.newsapp_mvvm_architecture.di.component

import android.content.Context
import com.ksa.newsapp_mvvm_architecture.NewsApplication
import com.ksa.newsapp_mvvm_architecture.data.api.NetworkService
import com.ksa.newsapp_mvvm_architecture.data.repository.CountryListRepository
import com.ksa.newsapp_mvvm_architecture.data.repository.NewsSearchByKeywordRepository
import com.ksa.newsapp_mvvm_architecture.data.repository.NewsSourcesRepository
import com.ksa.newsapp_mvvm_architecture.data.repository.TopHeadlinesRepository
import com.ksa.newsapp_mvvm_architecture.di.ApplicationContext
import com.ksa.newsapp_mvvm_architecture.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getApplicationContext(): Context

    fun getNetworkService(): NetworkService

    fun getTopHeadlineRepository(): TopHeadlinesRepository

    fun getNewsSourceRepository(): NewsSourcesRepository

    fun getCountryListRepository(): CountryListRepository

    fun getSearchNewsRepository(): NewsSearchByKeywordRepository
}