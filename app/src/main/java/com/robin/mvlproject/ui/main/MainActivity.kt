package com.robin.mvlproject.ui.main

import android.os.Bundle
import com.robin.mvlproject.R
import com.robin.mvlproject.base.BaseActivity
import com.robin.mvlproject.databinding.ActivityMainBinding
import com.robin.mvlproject.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main
) {
    override fun start() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fcv_container, HomeFragment.instance)
            .commit()
    }

    override fun observe() {
    }

}