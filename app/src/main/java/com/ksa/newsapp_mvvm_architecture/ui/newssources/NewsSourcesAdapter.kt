package com.ksa.newsapp_mvvm_architecture.ui.newssources

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ksa.newsapp_mvvm_architecture.data.model.Source
import com.ksa.newsapp_mvvm_architecture.databinding.NewsSourceItemBinding
import com.ksa.newsapp_mvvm_architecture.ui.topheadline.TopHeadlinesActivity

class NewsSourcesAdapter(
    private val sourceList: ArrayList<Source>
) : RecyclerView.Adapter<NewsSourcesAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: NewsSourceItemBinding,
                         private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(source: Source) {
            binding.topheadlinesButton.text = source.name
            binding.topheadlinesButton.setOnClickListener {
                context.startActivity(TopHeadlinesActivity.startHeadlinesActivity(
                    context,"",source.name
                ))

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : DataViewHolder{
        return DataViewHolder(
            NewsSourceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),parent.context
        )}

    override fun getItemCount(): Int = sourceList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(sourceList[position])

    fun addData(list: List<Source>) {
        sourceList.addAll(list)
    }
}