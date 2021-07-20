package com.robin.mvlproject.ui.labellog

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseFragment
import com.robin.mvlproject.base.LABEL_TYPE
import com.robin.mvlproject.data.entities.LabelType
import com.robin.mvlproject.databinding.FragmentLabelLogBinding
import com.robin.mvlproject.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LabelLogFragment : BaseFragment<FragmentLabelLogBinding>(
    R.layout.fragment_label_log
) {

    private val viewModel: LabelLogViewModel by viewModels()

    private val sharedViewModel: MainViewModel by activityViewModels()

    private val adapter: LabelLogAdapter by lazy {
        LabelLogAdapter(viewModel)
    }

    override fun start() {
        binding.rvLabelLogList.adapter = adapter
        viewModel.loadData(requireArguments().getSerializable(LABEL_TYPE) as LabelType)
    }

    override fun observe() {
        with(viewModel) {
            labelList.observe(viewLifecycleOwner, {
                adapter.submitList(it)
            })
            actionLabelItemClicked.observe(viewLifecycleOwner, { event ->
                event.getContentIfNotHandled()?.let {
                    sharedViewModel.updateLabel(it)
                }
            })
        }
    }

    companion object {
        fun getInstance(labelType: LabelType) = LabelLogFragment().apply {
            arguments = Bundle().apply {
                putSerializable(LABEL_TYPE, labelType)
            }
        }
    }
}