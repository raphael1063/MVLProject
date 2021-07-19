package com.robin.mvlproject.ui.labellog

import androidx.fragment.app.viewModels
import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseFragment
import com.robin.mvlproject.databinding.FragmentLabelLogBinding

class LabelLogFragment : BaseFragment<FragmentLabelLogBinding>(
    R.layout.fragment_label_log
) {

    private val viewModel: LabelLogViewModel by viewModels()

    private val adapter: LabelLogAdapter by lazy {
        LabelLogAdapter(viewModel)
    }

    override fun start() {
    }

    override fun observe() {
    }
}