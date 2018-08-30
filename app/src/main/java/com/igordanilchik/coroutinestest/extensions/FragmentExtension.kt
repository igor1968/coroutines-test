package com.igordanilchik.coroutinestest.extensions

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

fun FragmentActivity.replaceFragment(containerId: Int, fragment: Fragment, addToBackStack: Boolean) {
    val fm = this.supportFragmentManager
    val ft = fm.beginTransaction()

    ft.replace(containerId, fragment, fragment.javaClass.name)
    if (addToBackStack) {
        ft.addToBackStack(null)
    }
    ft.commit()
}
