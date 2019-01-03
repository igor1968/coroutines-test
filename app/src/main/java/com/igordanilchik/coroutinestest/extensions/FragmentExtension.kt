package com.igordanilchik.coroutinestest.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.replaceFragment(containerId: Int, fragment: Fragment, addToBackStack: Boolean) {
    val fm = this.supportFragmentManager
    val ft = fm.beginTransaction()

    ft.replace(containerId, fragment, fragment.javaClass.name)
    if (addToBackStack) {
        ft.addToBackStack(null)
    }
    ft.commit()
}
