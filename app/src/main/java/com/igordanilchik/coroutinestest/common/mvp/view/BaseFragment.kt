package com.igordanilchik.coroutinestest.common.mvp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.arellomobile.mvp.MvpAppCompatFragment
import com.igordanilchik.coroutinestest.app.DaggerApplication
import com.igordanilchik.coroutinestest.common.di.ApplicationComponent

/**
 * @author Igor Danilchik
 */
abstract class BaseFragment : MvpAppCompatFragment() {

    abstract val layoutResID: Int

    @StringRes
    open val baseTitle: Int? = null

    fun appComponent(): ApplicationComponent = DaggerApplication[context].appComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutResID, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        baseTitle?.let { setTitle(getString(it)) }
    }

    protected fun setTitle(title: CharSequence) {
        activity?.apply {
            if (!isFinishing)
                setTitle(title)
        }
    }
}

