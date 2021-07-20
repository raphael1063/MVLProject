package com.robin.mvlproject.ui.detail

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseFragment
import com.robin.mvlproject.data.entities.Label
import com.robin.mvlproject.databinding.FragmentDetailBinding
import com.robin.mvlproject.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(
    R.layout.fragment_detail
) {

    private val viewModel: DetailViewModel by viewModels()

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun start() {
        binding.vm = viewModel
        requireArguments().getParcelable<Label>("label")?.let { label ->
            viewModel.loadData(label)
        }
    }

    override fun observe() {
        viewModel.updateLabel.observe(viewLifecycleOwner, {
            sharedViewModel.updateLabel(it)
        })
    }

    companion object {
        fun getInstance(label: Label): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("label", label)
                }
            }
        }
    }
}