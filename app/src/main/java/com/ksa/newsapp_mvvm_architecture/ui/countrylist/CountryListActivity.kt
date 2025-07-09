package com.ksa.newsapp_mvvm_architecture.ui.countrylist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ksa.newsapp_mvvm_architecture.NewsApplication
import com.ksa.newsapp_mvvm_architecture.data.model.Country
import com.ksa.newsapp_mvvm_architecture.databinding.ActivityCountryListBinding
import com.ksa.newsapp_mvvm_architecture.di.component.DaggerActivityComponent
import com.ksa.newsapp_mvvm_architecture.di.module.ActivityModule
import com.ksa.newsapp_mvvm_architecture.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryListActivity : AppCompatActivity() {

    @Inject
    lateinit var countryListViewModel: CountryListViewModel

    @Inject
    lateinit var countryListAdapter: CountryListAdapter

    private lateinit var countryListBinding: ActivityCountryListBinding
    private lateinit var errorView: View
    private lateinit var retryBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        countryListBinding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(countryListBinding.root)
        setUpUi()
        setupObserver()
    }

    companion object {
        fun startCountryListActivity(activity: Activity, bundleParam : String="") : Intent {
            return Intent(activity, CountryListActivity::class.java)
        }
    }

    private fun setUpUi() {
        errorView = countryListBinding.errorLayout.errorConstraintlayout
        retryBtn = countryListBinding.errorLayout.retryButton
        countryListBinding.recyclerView.layoutManager = GridLayoutManager(this,2)
        countryListBinding.recyclerView.adapter = countryListAdapter

        retryBtn.setOnClickListener {
            countryListViewModel.getCountryList()
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            countryListViewModel.uiState.collect{
                when (it) {
                    is UiState.Success -> {
                        countryListBinding.progressBar.visibility = View.GONE
                        renderList(it.data)
                        countryListBinding.recyclerView.visibility = View.VISIBLE
                    }
                    is UiState.Loading -> {
                        countryListBinding.progressBar.visibility = View.VISIBLE
                        countryListBinding.recyclerView.visibility = View.GONE
                    }
                    is UiState.Error -> {

                        countryListBinding.progressBar.visibility = View.GONE
                        countryListBinding.recyclerView.visibility = View.GONE
                        errorView.visibility = View.VISIBLE
                        Toast.makeText(this@CountryListActivity, it.message, Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun renderList(countriesList: List<Country>) {
        countryListAdapter.addData(countriesList)
        countryListAdapter.notifyDataSetChanged()
    }

    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build().injectCountryList(this)
    }
}