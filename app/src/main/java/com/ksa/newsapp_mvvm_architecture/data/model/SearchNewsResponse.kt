package com.ksa.newsapp_mvvm_architecture.data.model

data class SearchNewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)