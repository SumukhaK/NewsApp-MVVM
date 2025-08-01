package com.ksa.newsapp_mvvm_architecture.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider {

    private val testDispatcherProvider = UnconfinedTestDispatcher()

    override val default: CoroutineDispatcher
        get() = testDispatcherProvider

    override val main: CoroutineDispatcher
        get() = testDispatcherProvider

    override val io: CoroutineDispatcher
        get() = testDispatcherProvider
}