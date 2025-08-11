package com.ksa.newsapp_mvvm_architecture.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksa.newsapp_mvvm_architecture.data.model.Article
import com.ksa.newsapp_mvvm_architecture.databinding.ActivityNewsSearchBinding
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import com.ksa.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsSearchActivity : AppCompatActivity() {

    private lateinit var searchActivityNewsSearchBinding: ActivityNewsSearchBinding
    private lateinit var searchViewModel: NewsSearchViewModel

    @Inject
    lateinit var adapter: TopHeadlineAdapter
    private lateinit var errorView: View
    private lateinit var retryBtn: Button
    private var submittedQuery = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchActivityNewsSearchBinding = ActivityNewsSearchBinding.inflate(layoutInflater)
        setContentView(searchActivityNewsSearchBinding.root)
        setupViewModel()
        setupUi()
        setupObsevers()
    }

    private fun setupObsevers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            searchActivityNewsSearchBinding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            searchActivityNewsSearchBinding.recyclerView.visibility = View.VISIBLE
                        }

                        is UiState.Loading -> {
                            searchActivityNewsSearchBinding.progressBar.visibility = View.VISIBLE
                            searchActivityNewsSearchBinding.recyclerView.visibility = View.GONE
                        }

                        is UiState.Error -> {
                            searchActivityNewsSearchBinding.progressBar.visibility = View.GONE
                            searchActivityNewsSearchBinding.recyclerView.visibility = View.GONE
                            errorView.visibility = View.VISIBLE
                            Toast.makeText(this@NewsSearchActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun setupUi() {
        val recyclerView = searchActivityNewsSearchBinding.recyclerView
        errorView = searchActivityNewsSearchBinding.errorLayout.errorConstraintlayout
        retryBtn = searchActivityNewsSearchBinding.errorLayout.retryButton
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter

        retryBtn.setOnClickListener {
            searchViewModel.searchNews(submittedQuery)
        }

        searchActivityNewsSearchBinding.searchview.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    submittedQuery = query
                    searchViewModel.searchNews(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    submittedQuery = newText
                    searchViewModel.searchNews(newText)
                }
                return false
            }
        })
    }

    companion object {
        fun startNewsSourcesActivity(context: Context, bundleParam: String = ""): Intent {
            return Intent(context, NewsSearchActivity::class.java)
        }
    }

    private fun renderList(articleList: List<Article>) {
        adapter.addData(articleList)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        searchViewModel = ViewModelProvider(this)[NewsSearchViewModel::class.java]
    }
}