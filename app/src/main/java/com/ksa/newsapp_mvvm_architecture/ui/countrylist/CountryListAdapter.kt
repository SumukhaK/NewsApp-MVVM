package com.ksa.newsapp_mvvm_architecture.ui.countrylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ksa.newsapp_mvvm_architecture.data.model.Country
import com.ksa.newsapp_mvvm_architecture.databinding.NewsSourceItemBinding

class CountryListAdapter(
    private val countriesList: ArrayList<Country>
) : RecyclerView.Adapter<CountryListAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: NewsSourceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            binding.topheadlinesButton.text = country.Name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        NewsSourceItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = countriesList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(countriesList[position])

    fun addData(list: List<Country>) {
        countriesList.addAll(list)
    }
}