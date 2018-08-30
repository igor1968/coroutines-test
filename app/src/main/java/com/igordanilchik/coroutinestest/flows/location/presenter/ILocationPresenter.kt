package com.igordanilchik.coroutinestest.flows.location.presenter

/**
 * @author Igor Danilchik
 */
interface ILocationPresenter {
    fun onMapReady()
    fun onPermissionsGranted()
    var zoomLevel: Float?
}