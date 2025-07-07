package com.ksa.newsapp_mvvm_architecture.data.model

data class NewsSourcesResponse(
    val sources: List<Source>,
    val status: String
)