package com.ksa.newsapp_mvvm_architecture.ui.topheadlines

import app.cash.turbine.test
import com.ksa.newsapp_mvvm_architecture.data.model.Article
import com.ksa.newsapp_mvvm_architecture.data.repository.TopHeadlinesRepository
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import com.ksa.newsapp_mvvm_architecture.ui.topheadline.TopHeadlinesViewModel
import com.ksa.newsapp_mvvm_architecture.utils.AppConstants.COUNTRY
import com.ksa.newsapp_mvvm_architecture.utils.DispatcherProvider
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopHeadlinesViewModelTest {
    @Mock
    private lateinit var topHeadlinesRepository: TopHeadlinesRepository

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setup(){
       // dispatcherProvider  = Tes
    }

    @Test
    fun fetchNews_whenRepositoryResponseSuccess_shouldSetSuccessUiState(){
        runTest {
            doReturn(flowOf(emptyList<Article>()))
                .`when`(topHeadlinesRepository)
                .getTopHeadlines(COUNTRY)
            val viewModel = TopHeadlinesViewModel(topHeadlinesRepository, dispatcherProvider)
            viewModel.uiState.test {
                assertEquals(UiState.Success(emptyList<Article>()),awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(topHeadlinesRepository, times(1)).getTopHeadlines(COUNTRY)
        }
    }
}