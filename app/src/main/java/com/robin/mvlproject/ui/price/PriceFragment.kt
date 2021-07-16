package com.robin.mvlproject.ui.price

import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseFragment
import com.robin.mvlproject.data.entities.BooksRequest
import com.robin.mvlproject.data.entities.Label
import com.robin.mvlproject.databinding.FragmentPriceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PriceFragment : BaseFragment<FragmentPriceBinding>(
    R.layout.fragment_price
) {

    private val viewModel: PriceViewModel by viewModels()

    override fun start() {
        binding.vm = viewModel
            viewModel.loadData(requireArguments().getParcelable("labelA")!!, requireArguments().getParcelable("labelB")!!)
    }

    override fun observe() {
    }

    companion object {
       fun getInstance(labelA: Label, labelB: Label) = PriceFragment().apply {
           arguments = Bundle().apply {
               putParcelable("labelA", labelA)
               putParcelable("labelB", labelB)
           }
       }
    }
}