package com.ksa.newsapp_mvvm_architecture.data.model

import com.google.gson.annotations.SerializedName
import com.ksa.newsapp_mvvm_architecture.data.local.entity.SourceEntity

data class Source(@SerializedName("id")
                  val id: String? = null,
                  @SerializedName("name")
                  val name: String = "",)

fun Source.toSourceEntity(): SourceEntity {
    return SourceEntity(id, name)
}