package com.robin.mvlproject.ui.labellog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.robin.mvlproject.R
import com.robin.mvlproject.data.entities.Label
import com.robin.mvlproject.databinding.ItemLabelLogBinding

class LabelLogAdapter(private val viewModel: LabelLogViewModel) : ListAdapter<Label, LabelLogAdapter.ViewHolder>(LABEL_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_label_log,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], viewModel)
    }

    class ViewHolder(private val binding: ItemLabelLogBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Label, viewModel: LabelLogViewModel) {
            with(binding) {
                this.model = model
                vm = viewModel
            }
        }
    }

    companion object {
        private val LABEL_COMPARATOR = object : DiffUtil.ItemCallback<Label>() {
            override fun areItemsTheSame(oldItem: Label, newItem: Label): Boolean {
                return oldItem.idx == newItem.idx
            }

            override fun areContentsTheSame(oldItem: Label, newItem: Label): Boolean {
                return oldItem == newItem
            }

        }
    }

}