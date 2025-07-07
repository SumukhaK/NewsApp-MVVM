package com.ksa.newsapp_mvvm_architecture.ui.newssources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ksa.newsapp_mvvm_architecture.data.model.Source
import com.ksa.newsapp_mvvm_architecture.databinding.NewsSourceItemBinding

class NewsSourcesAdapter(
    private val sourceList: ArrayList<Source>
) : RecyclerView.Adapter<NewsSourcesAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: NewsSourceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(source: Source) {
            binding.topheadlinesButton.text = source.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        NewsSourceItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = sourceList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(sourceList[position])

    fun addData(list: List<Source>) {
        sourceList.addAll(list)
    }
}