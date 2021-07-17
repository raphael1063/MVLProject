package com.robin.mvlproject.ui.history

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseFragment
import com.robin.mvlproject.databinding.FragmentHistoryBinding
import com.robin.mvlproject.databinding.FragmentPriceBinding
import com.robin.mvlproject.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(
    R.layout.fragment_history
) {

    private val viewModel: HistoryViewModel by viewModels()

    private val sharedViewModel: MainViewModel by activityViewModels()

    private val adapter by lazy {
        HistoryAdapter(viewModel)
    }

    override fun start() {
        with(binding) {
            vm = viewModel
            rvUsageList.adapter = adapter
        }
    }

    override fun observe() {
        viewModel.historyList.observe(this, {
            adapter.submitList(it)
        })
    }

    companion object {
        fun getInstance() = HistoryFragment()
    }
}