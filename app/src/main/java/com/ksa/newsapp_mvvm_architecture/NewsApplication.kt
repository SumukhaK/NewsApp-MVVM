package com.ksa.newsapp_mvvm_architecture

import android.app.Application
import com.ksa.newsapp_mvvm_architecture.di.component.ApplicationComponent
import com.ksa.newsapp_mvvm_architecture.di.component.DaggerApplicationComponent
import com.ksa.newsapp_mvvm_architecture.di.module.ApplicationModule

class NewsApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}