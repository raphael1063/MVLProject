package com.robin.mvlproject.ui.main

import androidx.activity.viewModels
import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseActivity
import com.robin.mvlproject.data.entities.Step.*
import com.robin.mvlproject.databinding.ActivityMainBinding
import com.robin.mvlproject.ui.detail.DetailFragment
import com.robin.mvlproject.ui.history.HistoryFragment
import com.robin.mvlproject.ui.home.HomeFragment
import com.robin.mvlproject.ui.price.PriceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {

    private val viewModel: MainViewModel by viewModels()

    override fun start() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fcv_container, HomeFragment.instance)
            .addToBackStack(null)
            .commit()
    }

    override fun observe() {
        with(viewModel) {
            openDetail.observe(this@MainActivity, { event ->
                event.getContentIfNotHandled()?.let { label ->
                    supportFragmentManager.beginTransaction()
                        .add(R.id.fcv_container, DetailFragment.getInstance(label))
                        .addToBackStack(null)
                        .commit()
                }
            })
            openPrice.observe(this@MainActivity, { event ->
                event.getContentIfNotHandled()?.let { label ->
                    supportFragmentManager.beginTransaction()
                        .add(R.id.fcv_container, PriceFragment.instance)
                        .addToBackStack(null)
                        .commit()
                }
            })
            openHistory.observe(this@MainActivity, { event ->
                event.getContentIfNotHandled()?.let {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.fcv_container, HistoryFragment.instance)
                        .addToBackStack(null)
                        .commit()
                }
            })
            updateLabel.observe(this@MainActivity, {
                super.onBackPressed()
            })
        }
    }

}