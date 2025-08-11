package com.ksa.newsapp_mvvm_architecture.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseURL

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DatabaseName

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DatabaseVersion

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkApiKey