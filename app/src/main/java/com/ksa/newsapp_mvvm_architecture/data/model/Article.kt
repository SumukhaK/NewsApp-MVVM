package com.ksa.newsapp_mvvm_architecture.data.model

import com.google.gson.annotations.SerializedName
import com.ksa.newsapp_mvvm_architecture.data.local.entity.ArticleEntity

data class Article(@SerializedName("title")
                   val title: String = "",
                   @SerializedName("description")
                   val description: String = "",
                   @SerializedName("url")
                   val url: String = "",
                   @SerializedName("urlToImage")
                   val imageUrl: String = "",
                   @SerializedName("source")
                   val source: Source)

fun Article.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        source = source.toSourceEntity()
    )
}
