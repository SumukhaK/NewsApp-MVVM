package com.ksa.newsapp_mvvm_architecture.ui.mainscreen

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ksa.newsapp_mvvm_architecture.R
import com.ksa.newsapp_mvvm_architecture.databinding.ActivityMainBinding
import com.ksa.newsapp_mvvm_architecture.databinding.ActivityTopHeadlinesBinding
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
    fun navigateToNewsSources(view: View) {}
    fun navigateToCountries(view: View) {}
    fun navigateToLanguages(view: View) {}
    fun navigateToSearch(view: View) {}
}