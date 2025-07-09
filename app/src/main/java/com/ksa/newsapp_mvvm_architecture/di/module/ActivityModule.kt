package com.ksa.newsapp_mvvm_architecture.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ksa.newsapp_mvvm_architecture.data.repository.CountryListRepository
import com.ksa.newsapp_mvvm_architecture.data.repository.NewsSourcesRepository
import com.ksa.newsapp_mvvm_architecture.data.repository.TopHeadlinesRepository
import com.ksa.newsapp_mvvm_architecture.di.ActivityContext
import com.ksa.newsapp_mvvm_architecture.ui.base.ViewModelProviderFactory
import com.ksa.newsapp_mvvm_architecture.ui.countrylist.CountryListAdapter
import com.ksa.newsapp_mvvm_architecture.ui.countrylist.CountryListViewModel
import com.ksa.newsapp_mvvm_architecture.ui.newssources.NewsSourcesAdapter
import com.ksa.newsapp_mvvm_architecture.ui.newssources.NewsSourcesViewModel
import com.ksa.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineAdapter
import com.ksa.newsapp_mvvm_architecture.ui.topheadline.TopHeadlinesViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {
    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideNewsListViewModel(topHeadlineRepository: TopHeadlinesRepository): TopHeadlinesViewModel {
        return ViewModelProvider(activity,
       factory = ViewModelProviderFactory(TopHeadlinesViewModel::class) {
                TopHeadlinesViewModel(topHeadlineRepository)
            })[TopHeadlinesViewModel::class.java]
    }

    @Provides
    fun provideNewsSourcesViewModel(newsSourcesRepository: NewsSourcesRepository): NewsSourcesViewModel {
        return ViewModelProvider(activity,
            factory = ViewModelProviderFactory(NewsSourcesViewModel::class){
                NewsSourcesViewModel(newsSourcesRepository)
            })[NewsSourcesViewModel::class.java]
    }

    @Provides
    fun provideCountryListViewModel(countryListRepository: CountryListRepository): CountryListViewModel {
        return ViewModelProvider(activity,
            factory = ViewModelProviderFactory(CountryListViewModel::class){
                CountryListViewModel(countryListRepository)
            })[CountryListViewModel::class.java]
    }

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun provideNewsSourcesAdapter() = NewsSourcesAdapter(ArrayList())

    @Provides
    fun providesCountryListAdapter() = CountryListAdapter(ArrayList())
}