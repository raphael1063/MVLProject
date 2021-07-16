package com.robin.mvlproject.ui.price

import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseFragment
import com.robin.mvlproject.databinding.FragmentPriceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PriceFragment : BaseFragment<FragmentPriceBinding>(
    R.layout.fragment_price
) {
    override fun start() {
    }

    override fun observe() {
    }

    companion object {
        val instance = PriceFragment()
    }
}