package com.robin.mvlproject.ui.history

import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseFragment
import com.robin.mvlproject.databinding.FragmentHistoryBinding
import com.robin.mvlproject.databinding.FragmentPriceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment  : BaseFragment<FragmentHistoryBinding>(
    R.layout.fragment_history
) {
    override fun start() {
    }

    override fun observe() {
    }

    companion object {
        val instance = HistoryFragment()
    }
}