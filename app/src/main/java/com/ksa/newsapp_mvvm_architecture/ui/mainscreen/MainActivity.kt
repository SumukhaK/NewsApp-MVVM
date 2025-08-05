package com.ksa.newsapp_mvvm_architecture.ui.mainscreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ksa.newsapp_mvvm_architecture.databinding.ActivityMainBinding
import com.ksa.newsapp_mvvm_architecture.ui.countrylist.CountryListActivity
import com.ksa.newsapp_mvvm_architecture.ui.newssources.NewsSourcesActivity
import com.ksa.newsapp_mvvm_architecture.ui.offlinefirst.ReadOfflineActivity
import com.ksa.newsapp_mvvm_architecture.ui.search.NewsSearchActivity
import com.ksa.newsapp_mvvm_architecture.ui.topheadline.TopHeadlinesActivity

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
    }

    fun navigateToTopHeadlines(view: View) {
        val intent = TopHeadlinesActivity.startHeadlinesActivity(this, "")
        startActivity(intent)
    }

    fun navigateToOfflineReading(view: View) {
        startActivity(ReadOfflineActivity.startOfflineActivity(this))
    }

    fun navigateToNewsSources(view: View) {
        startActivity(NewsSourcesActivity.startNewsSourcesActivity(this))
    }

    fun navigateToCountries(view: View) {
        startActivity(CountryListActivity.startCountryListActivity(this))
    }

    fun navigateToLanguages(view: View) {
        Toast.makeText(
            this,
            "News in different languages coming soon..", Toast.LENGTH_LONG
        ).show()
    }

    fun navigateToSearch(view: View) {
        startActivity(NewsSearchActivity.startNewsSourcesActivity(this))
    }
}