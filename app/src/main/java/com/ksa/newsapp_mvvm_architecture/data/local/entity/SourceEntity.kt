package com.ksa.newsapp_mvvm_architecture.data.local.entity

import androidx.room.ColumnInfo

data class SourceEntity(
    @ColumnInfo(name = "sourceId")
    val id: String?,
    @ColumnInfo(name = "sourceName")
    val name: String = ""
)
