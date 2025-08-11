package com.ksa.newsapp_mvvm_architecture.ui.topheadline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksa.newsapp_mvvm_architecture.data.model.Article
import com.ksa.newsapp_mvvm_architecture.databinding.ActivityTopHeadlinesBinding
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import com.ksa.newsapp_mvvm_architecture.utils.AppConstants.COUNTRY
import com.ksa.newsapp_mvvm_architecture.utils.AppConstants.COUNTRY_BUNDLE_KEY
import com.ksa.newsapp_mvvm_architecture.utils.AppConstants.NEWS_SOURCES_BUNDLE_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopHeadlinesActivity : AppCompatActivity() {

    private lateinit var newsListViewModel: TopHeadlinesViewModel

    @Inject
    lateinit var adapter: TopHeadlineAdapter

    private lateinit var binding: ActivityTopHeadlinesBinding
    private lateinit var errorView: View
    private lateinit var retryBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlinesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        getExtrasFromIntent()
        setupUI()
        setupObserver()
    }

    private fun getExtrasFromIntent() {
        val country = intent.getStringExtra(COUNTRY_BUNDLE_KEY)
            ?: COUNTRY
        val source = intent.getStringExtra(NEWS_SOURCES_BUNDLE_KEY) ?: ""
        if (country.isNotEmpty()) {
            if (!country.contentEquals("us")) {
                newsListViewModel.fetchNews(country)
            }
        }

        if (!source.isNullOrBlank()) {
            newsListViewModel.fetchNewsFromSource(source)
        } else {
            newsListViewModel.fetchNews()
        }
    }

    companion object {
        fun startHeadlinesActivity(context: Context, country: String, source: String = "")
                : Intent {
            return Intent(context, TopHeadlinesActivity::class.java).apply {
                this.putExtra(COUNTRY_BUNDLE_KEY, country)
                this.putExtra(NEWS_SOURCES_BUNDLE_KEY, source)
            }
        }
    }

    private fun setupUI() {
        errorView = binding.errorLayout.errorConstraintlayout
        retryBtn = binding.errorLayout.retryButton
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter

        retryBtn.setOnClickListener {
            retryFetchingNews()
        }

    }

    private fun retryFetchingNews() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        errorView.visibility = View.GONE
        newsListViewModel.fetchNews("us")
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            binding.recyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.GONE
                            errorView.visibility = View.VISIBLE
                            Toast.makeText(
                                this@TopHeadlinesActivity,
                                it.message, Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        adapter.addData(articleList)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        newsListViewModel = ViewModelProvider(this)[TopHeadlinesViewModel::class.java]
    }
}