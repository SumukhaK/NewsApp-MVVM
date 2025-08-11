package com.ksa.newsapp_mvvm_architecture.di.module

import com.ksa.newsapp_mvvm_architecture.ui.countrylist.CountryListAdapter
import com.ksa.newsapp_mvvm_architecture.ui.newssources.NewsSourcesAdapter
import com.ksa.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @ActivityScoped
    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @ActivityScoped
    @Provides
    fun provideNewsSourcesAdapter() = NewsSourcesAdapter(ArrayList())

    @ActivityScoped
    @Provides
    fun providesCountryListAdapter() = CountryListAdapter(ArrayList())
}