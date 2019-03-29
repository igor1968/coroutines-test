package com.igordanilchik.coroutinestest.flows.location.presenter

import com.arellomobile.mvp.InjectViewState
import com.igordanilchik.coroutinestest.common.mvp.presenter.AppBasePresenter
import com.igordanilchik.coroutinestest.flows.location.model.ILocationModel
import com.igordanilchik.coroutinestest.flows.location.view.LocationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author Igor Danilchik
 */
@InjectViewState
class LocationPresenter(
    private val model: ILocationModel
) : AppBasePresenter<LocationView>(), ILocationPresenter {

    override var zoomLevel: Float? = null

    override fun attachView(view: LocationView?) {
        super.attachView(view)

        viewState.requestMap()
    }

    override fun onMapReady() = viewState.requestPermissions()

    override fun onPermissionsGranted() {

        GlobalScope.launch(Dispatchers.Main) {
            try {
                Timber.d("Start consume locations")
                model.location().consumeEach { location ->
                    Timber.d("Location consumed")
                    val address = model.address(location)
                    viewState.updateMap(location, address, zoomLevel)
                }
            } catch (e: Throwable) {
                viewState.showError(e)
            }
        }
    }

    override fun destroyView(view: LocationView?) {
        super.destroyView(view)
        model.dispose()
    }
}