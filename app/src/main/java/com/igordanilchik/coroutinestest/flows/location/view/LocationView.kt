package com.igordanilchik.coroutinestest.flows.location.view

import android.location.Location
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.igordanilchik.coroutinestest.common.mvp.view.AppBaseView
import com.igordanilchik.coroutinestest.flows.location.router.LocationRouter

/**
 * @author Igor Danilchik
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface LocationView: AppBaseView, LocationRouter {
    fun requestMap()
    fun showError(e: Throwable)
    fun updateMap(location: Location, address: String, zoom: Float?)
    fun requestPermissions()
}