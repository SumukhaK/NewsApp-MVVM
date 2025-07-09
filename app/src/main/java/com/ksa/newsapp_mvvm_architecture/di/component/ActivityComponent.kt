package com.ksa.newsapp_mvvm_architecture.di.component

import com.ksa.newsapp_mvvm_architecture.di.ActivityScope
import com.ksa.newsapp_mvvm_architecture.di.module.ActivityModule
import com.ksa.newsapp_mvvm_architecture.ui.countrylist.CountryListActivity
import com.ksa.newsapp_mvvm_architecture.ui.newssources.NewsSourcesActivity
import com.ksa.newsapp_mvvm_architecture.ui.topheadline.TopHeadlinesActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: TopHeadlinesActivity)
    fun injectNewsSources(activity: NewsSourcesActivity)
    fun injectCountryList(activity: CountryListActivity)
}