package com.ksa.newsapp_mvvm_architecture.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ksa.newsapp_mvvm_architecture.data.repository.CountryListRepository
import com.ksa.newsapp_mvvm_architecture.data.repository.NewsSearchByKeywordRepository
import com.ksa.newsapp_mvvm_architecture.data.repository.NewsSourcesRepository
import com.ksa.newsapp_mvvm_architecture.data.repository.TopHeadlinesRepository
import com.ksa.newsapp_mvvm_architecture.di.ActivityContext
import com.ksa.newsapp_mvvm_architecture.ui.base.ViewModelProviderFactory
import com.ksa.newsapp_mvvm_architecture.ui.countrylist.CountryListAdapter
import com.ksa.newsapp_mvvm_architecture.ui.countrylist.CountryListViewModel
import com.ksa.newsapp_mvvm_architecture.ui.newssources.NewsSourcesAdapter
import com.ksa.newsapp_mvvm_architecture.ui.newssources.NewsSourcesViewModel
import com.ksa.newsapp_mvvm_architecture.ui.search.NewsSearchViewModel
import com.ksa.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineAdapter
import com.ksa.newsapp_mvvm_architecture.ui.topheadline.TopHeadlinesViewModel
import com.ksa.newsapp_mvvm_architecture.utils.DispatcherProvider
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
    fun provideNewsListViewModel(topHeadlineRepository: TopHeadlinesRepository,
                                 dispatcherProvider: DispatcherProvider):
            TopHeadlinesViewModel {
        return ViewModelProvider(activity,
            factory = ViewModelProviderFactory(TopHeadlinesViewModel::class) {
                TopHeadlinesViewModel(topHeadlineRepository,dispatcherProvider)
            })[TopHeadlinesViewModel::class.java]
    }

    @Provides
    fun provideNewsSourcesViewModel(newsSourcesRepository: NewsSourcesRepository,
                                    dispatcherProvider: DispatcherProvider):
            NewsSourcesViewModel {
        return ViewModelProvider(activity,
            factory = ViewModelProviderFactory(NewsSourcesViewModel::class){
                NewsSourcesViewModel(newsSourcesRepository,dispatcherProvider)
            })[NewsSourcesViewModel::class.java]
    }

    @Provides
    fun provideCountryListViewModel(countryListRepository: CountryListRepository,
                                    dispatcherProvider: DispatcherProvider):
            CountryListViewModel {
        return ViewModelProvider(activity,
            factory = ViewModelProviderFactory(CountryListViewModel::class){
                CountryListViewModel(countryListRepository,dispatcherProvider)
            })[CountryListViewModel::class.java]
    }

    @Provides
    fun providesSearchViewModel(searchByKeywordRepository: NewsSearchByKeywordRepository,
                                dispatcherProvider: DispatcherProvider):
            NewsSearchViewModel{
        return ViewModelProvider(activity,
            factory = ViewModelProviderFactory(NewsSearchViewModel::class){
                NewsSearchViewModel(searchByKeywordRepository,dispatcherProvider)
            })[NewsSearchViewModel::class.java]
    }

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun provideNewsSourcesAdapter() = NewsSourcesAdapter(ArrayList())

    @Provides
    fun providesCountryListAdapter() = CountryListAdapter(ArrayList())
}