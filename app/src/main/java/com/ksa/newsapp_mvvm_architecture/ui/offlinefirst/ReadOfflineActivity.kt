package com.ksa.newsapp_mvvm_architecture.ui.offlinefirst

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
import com.ksa.newsapp_mvvm_architecture.data.local.entity.ArticleEntity
import com.ksa.newsapp_mvvm_architecture.data.model.Article
import com.ksa.newsapp_mvvm_architecture.data.model.Source
import com.ksa.newsapp_mvvm_architecture.databinding.ActivityTopHeadlinesBinding
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import com.ksa.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ReadOfflineActivity : AppCompatActivity() {

    private lateinit var offlineFirstViewModel: OfflineFirstViewModel

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
        setupUI()
        setupObserver()
    }

    companion object {
        fun startOfflineActivity(context: Context): Intent {
            return Intent(context, ReadOfflineActivity::class.java)
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
        offlineFirstViewModel.retryFetchingArticles()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                offlineFirstViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            renderOfflineFirstList(it.data)
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
                                this@ReadOfflineActivity,
                                it.message, Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderOfflineFirstList(articlesList: List<ArticleEntity>) {
        val mutableList = mutableListOf<Article>()
        articlesList.map {
            val singleArticle = Article(
                it.title, it.description ?: "", it.url, it.imageUrl ?: "",
                Source(it.source.id, it.source.name)
            )
            mutableList.add(singleArticle)
        }
        adapter.addData(mutableList)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        offlineFirstViewModel = ViewModelProvider(this)[OfflineFirstViewModel::class.java]
    }
}