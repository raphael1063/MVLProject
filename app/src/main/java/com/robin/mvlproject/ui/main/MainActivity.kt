package com.robin.mvlproject.ui.main

import androidx.activity.viewModels
import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseActivity
import com.robin.mvlproject.data.entities.Step.*
import com.robin.mvlproject.databinding.ActivityMainBinding
import com.robin.mvlproject.ext.addFragment
import com.robin.mvlproject.ui.detail.DetailFragment
import com.robin.mvlproject.ui.history.HistoryFragment
import com.robin.mvlproject.ui.home.HomeFragment
import com.robin.mvlproject.ui.labellog.LabelLogFragment
import com.robin.mvlproject.ui.price.PriceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun start() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fcv_container, HomeFragment.getInstance())
            .commit()
    }

    override fun observe() {
        with(viewModel) {
            openLabelLog.observe(this@MainActivity, { event ->
                event.getContentIfNotHandled()?.let { labelType ->
                    addFragment(LabelLogFragment.getInstance(labelType), R.id.fcv_container, true)
                }
            })
            openDetail.observe(this@MainActivity, { event ->
                event.getContentIfNotHandled()?.let { label ->
                    addFragment(DetailFragment.getInstance(label), R.id.fcv_container, true)
                }
            })
            openPrice.observe(this@MainActivity, { event ->
                event.getContentIfNotHandled()?.let { list ->
                    addFragment(PriceFragment.getInstance(list[0], list[1]), R.id.fcv_container, true)
                }
            })
            openHistory.observe(this@MainActivity, { event ->
                event.getContentIfNotHandled()?.let {
                    addFragment(HistoryFragment.getInstance(), R.id.fcv_container, true)
                }
            })
            updateLabel.observe(this@MainActivity, {
                super.onBackPressed()
            })
            moveToPosition.observe(this@MainActivity, {
                super.onBackPressed()
                super.onBackPressed()
            })
        }
    }

}