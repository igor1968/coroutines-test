package com.igordanilchik.coroutinestest.common.mvp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.arellomobile.mvp.MvpAppCompatFragment
import com.igordanilchik.coroutinestest.app.DaggerApplication
import com.igordanilchik.coroutinestest.common.di.ApplicationComponent

/**
 * @author Igor Danilchik
 */
abstract class BaseFragment: MvpAppCompatFragment() {

    private var unbinder: Unbinder? = null

    abstract val layoutResID: Int

    fun appComponent(): ApplicationComponent = DaggerApplication[context].appComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutResID, container, false)
        unbinder = ButterKnife.bind(this, view)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        unbinder?.unbind()
    }


}

