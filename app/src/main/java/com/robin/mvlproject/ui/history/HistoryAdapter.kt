package com.robin.mvlproject.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.robin.mvlproject.R
import com.robin.mvlproject.data.entities.BooksResult
import com.robin.mvlproject.databinding.ItemHistoryBinding

class HistoryAdapter(private val viewModel: HistoryViewModel) :
    ListAdapter<BooksResult, HistoryAdapter.ViewHolder>(BOOK_COMPARATOR){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_history, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], viewModel)
    }

    class ViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(model: BooksResult, viewModel: HistoryViewModel) {
            with(binding) {
                this.model = model
                vm = viewModel
            }
        }
    }

    companion object {
        private val BOOK_COMPARATOR = object : DiffUtil.ItemCallback<BooksResult>() {
            override fun areItemsTheSame(oldItem: BooksResult, newItem: BooksResult): Boolean {
                return oldItem.idx == newItem.idx
            }

            override fun areContentsTheSame(oldItem: BooksResult, newItem: BooksResult): Boolean {
                return oldItem == newItem
            }

        }
    }
}