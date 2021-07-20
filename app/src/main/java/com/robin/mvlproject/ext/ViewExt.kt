package com.robin.mvlproject.ext

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.robin.mvlproject.util.OnSafeClickListener

@BindingAdapter("onSafeClick")
fun View.setOnSafeClick(clickListener: View.OnClickListener?) {
    setOnClickListener(OnSafeClickListener(clickListener))
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, addToStack: Boolean) {
    supportFragmentManager.inTransaction {
        if (addToStack) add(frameId, fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName)
        else add(frameId, fragment)
    }
}