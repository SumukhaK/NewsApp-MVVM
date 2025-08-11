package com.ksa.newsapp_mvvm_architecture.di.module

import android.content.Context
import androidx.room.Room
import com.ksa.newsapp_mvvm_architecture.data.api.NetworkService
import com.ksa.newsapp_mvvm_architecture.data.local.AppDatabase
import com.ksa.newsapp_mvvm_architecture.data.local.AppDatabaseService
import com.ksa.newsapp_mvvm_architecture.data.local.DatabaseService
import com.ksa.newsapp_mvvm_architecture.di.BaseURL
import com.ksa.newsapp_mvvm_architecture.di.DatabaseName
import com.ksa.newsapp_mvvm_architecture.utils.DefaultDispatcherProvider
import com.ksa.newsapp_mvvm_architecture.utils.DefaultNetworkHelper
import com.ksa.newsapp_mvvm_architecture.utils.DispatcherProvider
import com.ksa.newsapp_mvvm_architecture.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @BaseURL
    @Provides
    fun provideBaseUrl(): String = "https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun getOkHttpBuilder(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient()
        val builder = httpClient.newBuilder()
        builder.connectTimeout(20, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.addInterceptor(interceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseURL baseURL: String,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(gsonConverterFactory)
            .client(getOkHttpBuilder())
            .build()
            .create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun providesDisptacherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return DefaultNetworkHelper(context)
    }

    @DatabaseName
    @Provides
    fun provideDatabaseName(): String = "news-db"

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            databaseName
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseService(appDatabase: AppDatabase): DatabaseService {
        return AppDatabaseService(appDatabase)
    }
}