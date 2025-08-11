package com.ksa.newsapp_mvvm_architecture.ui.newssources

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
import androidx.recyclerview.widget.GridLayoutManager
import com.ksa.newsapp_mvvm_architecture.data.model.Source
import com.ksa.newsapp_mvvm_architecture.databinding.ActivityNewsSourcesBinding
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsSourcesActivity : AppCompatActivity(){

    private lateinit var newsSourcesViewModel: NewsSourcesViewModel
    @Inject
    lateinit var newsSourcesAdapter: NewsSourcesAdapter
    lateinit var newsSourcesBinding: ActivityNewsSourcesBinding
    private lateinit var errorView: View
    private lateinit var retryBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsSourcesBinding = ActivityNewsSourcesBinding.inflate(layoutInflater)
        setContentView(newsSourcesBinding.root)
        setupViewModel()
        setUpUi()
        setupObserver()
    }

    private fun setUpUi() {
        errorView = newsSourcesBinding.errorLayout.errorConstraintlayout
        retryBtn = newsSourcesBinding.errorLayout.retryButton
        newsSourcesBinding.recyclerView.layoutManager = GridLayoutManager(this,2)
        newsSourcesBinding.recyclerView.adapter = newsSourcesAdapter

        retryBtn.setOnClickListener {
            newsSourcesViewModel.getNewsSources()
        }
    }

    companion object{
        fun startNewsSourcesActivity(context: Context, bundleParam : String="")
                : Intent{

            return Intent(context,NewsSourcesActivity::class.java)/*.apply {
               this.putExtra(NEWS_SOURCES_BUNDLE_KEY,bundleParam)  // uncomment and use it in
                // the future if required
           }*/
        }
    }

    private fun retryFetchingNewsSources() {
        newsSourcesBinding.progressBar.visibility = View.VISIBLE
        newsSourcesBinding.recyclerView.visibility = View.GONE
        errorView.visibility = View.GONE
        newsSourcesViewModel.getNewsSources()
    }
    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsSourcesViewModel.uiState.collect {
                    when (it) {
                        is UiState.Success -> {
                            newsSourcesBinding.progressBar.visibility = View.GONE
                            renderList(it.data)
                            newsSourcesBinding.recyclerView.visibility = View.VISIBLE
                        }
                        is UiState.Loading -> {
                            newsSourcesBinding.progressBar.visibility = View.VISIBLE
                            newsSourcesBinding.recyclerView.visibility = View.GONE
                        }
                        is UiState.Error -> {
                            newsSourcesBinding.progressBar.visibility = View.GONE
                            newsSourcesBinding.recyclerView.visibility = View.GONE
                            errorView.visibility = View.VISIBLE
                            Toast.makeText(this@NewsSourcesActivity,
                                it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderList(sourceList: List<Source>) {
        newsSourcesAdapter.addData(sourceList)
        newsSourcesAdapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        newsSourcesViewModel = ViewModelProvider(this)[NewsSourcesViewModel::class.java]
    }

}