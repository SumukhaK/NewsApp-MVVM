package com.ksa.newsapp_mvvm_architecture.ui.topheadline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ksa.newsapp_mvvm_architecture.R
import com.ksa.newsapp_mvvm_architecture.databinding.ActivityTopHeadlinesBinding
import javax.inject.Inject

class TopHeadlinesActivity : AppCompatActivity() {
    @Inject
    lateinit var newsListViewModel: TopHeadlinesViewModel

    @Inject
    lateinit var adapter: TopHeadlineAdapter

    private lateinit var binding: ActivityTopHeadlinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_top_headlines)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }
}