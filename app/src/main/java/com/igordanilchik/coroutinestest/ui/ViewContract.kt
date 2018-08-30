package com.igordanilchik.coroutinestest.ui

import android.os.Bundle


interface ViewContract {
    fun goToCategory(bundle: Bundle)
    fun goToOffer(bundle: Bundle)
}
