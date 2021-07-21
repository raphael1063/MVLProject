package com.robin.mvlproject.ui.price

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseFragment
import com.robin.mvlproject.base.LABEL_A
import com.robin.mvlproject.base.LABEL_B
import com.robin.mvlproject.data.entities.Label
import com.robin.mvlproject.databinding.FragmentPriceBinding
import com.robin.mvlproject.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PriceFragment : BaseFragment<FragmentPriceBinding>(
    R.layout.fragment_price
) {

    private val viewModel: PriceViewModel by viewModels()

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun start() {
        binding.vm = viewModel
            viewModel.loadData(requireArguments().getParcelable(LABEL_A)!!, requireArguments().getParcelable(LABEL_B)!!)
    }

    override fun observe() {
        viewModel.actionHistoryButtonClicked.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                sharedViewModel.openHistory()
            }
        })
    }

    companion object {
       fun getInstance(labelA: Label, labelB: Label) = PriceFragment().apply {
           arguments = Bundle().apply {
               putParcelable(LABEL_A, labelA)
               putParcelable(LABEL_B, labelB)
           }
       }
    }
}