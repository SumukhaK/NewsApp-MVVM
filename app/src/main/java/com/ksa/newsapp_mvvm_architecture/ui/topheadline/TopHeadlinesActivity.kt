package com.ksa.newsapp_mvvm_architecture.ui.topheadline

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksa.newsapp_mvvm_architecture.NewsApplication
import com.ksa.newsapp_mvvm_architecture.data.model.Article
import com.ksa.newsapp_mvvm_architecture.databinding.ActivityTopHeadlinesBinding
import com.ksa.newsapp_mvvm_architecture.databinding.DisplayErrorMessageBinding
import com.ksa.newsapp_mvvm_architecture.di.component.DaggerActivityComponent
import com.ksa.newsapp_mvvm_architecture.di.module.ActivityModule
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import com.ksa.newsapp_mvvm_architecture.utils.AppConstants
import com.ksa.newsapp_mvvm_architecture.utils.AppConstants.COUNTRY
import com.ksa.newsapp_mvvm_architecture.utils.AppConstants.COUNTRY_BUNDLE_KEY
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlinesActivity : AppCompatActivity() {
    @Inject
    lateinit var newsListViewModel: TopHeadlinesViewModel

    @Inject
    lateinit var adapter: TopHeadlineAdapter

    private lateinit var binding: ActivityTopHeadlinesBinding
    private lateinit var errorView: View
    private lateinit var retryBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlinesBinding.inflate(layoutInflater)
        errorView = binding.errorLayout.errorConstraintlayout
        retryBtn = binding.errorLayout.retryButton
        setContentView(binding.root)
        getExtrasFromIntent()
        setupUI()
        setupObserver()
    }

    private fun getExtrasFromIntent() {
        val country = intent.getStringExtra(COUNTRY_BUNDLE_KEY)
            ?: COUNTRY
        //Log.d("COUNTRY in getExtrasFromIntent",country)
        if(country.isNotEmpty()){
            if(!country.contentEquals("us")){
                //Log.d("COUNTRY calling fetchNews with ",country)
                newsListViewModel.fetchNews(country)
            }
        }
    }

    companion object {
        fun startHeadlinesActivity(activity: Activity, country : String) : Intent {
            return Intent(activity, TopHeadlinesActivity::class.java).apply {
                    this.putExtra(COUNTRY_BUNDLE_KEY,country)
            }
        }
    }

    private fun setupUI() {
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
                            //Handle Error
                            binding.progressBar.visibility = View.GONE
                            binding.recyclerView.visibility = View.GONE
                            errorView.visibility = View.VISIBLE
                            Toast.makeText(this@TopHeadlinesActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(articleList: List<Article>) {
        Log.d("UiState.Success Adaptor"," articleList : "+articleList.toString())
        adapter.addData(articleList)
        adapter.notifyDataSetChanged()
    }

    private fun injectDependencies(){
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this)).build().inject(this)
    }
}