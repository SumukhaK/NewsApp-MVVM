package com.ksa.newsapp_mvvm_architecture.di.component

import com.ksa.newsapp_mvvm_architecture.di.ActivityScope
import com.ksa.newsapp_mvvm_architecture.di.module.ActivityModule
import com.ksa.newsapp_mvvm_architecture.ui.base.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(mainActivity : MainActivity)
}