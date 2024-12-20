package com.ksa.newsapp_mvvm_architecture.di.module

import android.content.Context
import com.ksa.newsapp_mvvm_architecture.di.ActivityContext
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val context: Context) {
    @ActivityContext
    @Provides
    fun provideActivityContext(): Context = context
}