package com.igordanilchik.coroutinestest.flows.location.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.florent37.runtimepermission.kotlin.PermissionException
import com.github.florent37.runtimepermission.kotlin.coroutines.experimental.askPermission
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.igordanilchik.coroutinestest.R
import com.igordanilchik.coroutinestest.common.mvp.view.BaseFragment
import com.igordanilchik.coroutinestest.flows.location.builder.LocationModule
import com.igordanilchik.coroutinestest.flows.location.presenter.LocationPresenter
import kotlinx.android.synthetic.main.fragment_location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author Igor Danilchik
 */
class LocationFragment : BaseFragment(), LocationView {

    @InjectPresenter
    lateinit var presenter: LocationPresenter

    private var map: GoogleMap? = null

    override val layoutResID: Int = R.layout.fragment_location

    override val baseTitle: Int? = R.string.marker_title

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        MapsInitializer.initialize(activity)
        map_view.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()

        if (activity?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED
            || activity?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED) {

            presenter.zoomLevel = map?.cameraPosition?.zoom
            map?.isMyLocationEnabled = false
        }
    }

    override fun requestMap() =
        map_view.getMapAsync {
            map = it
            presenter.onMapReady()
        }

    override fun requestPermissions() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                askPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                //all permissions already granted or just granted

                presenter.onPermissionsGranted()
            } catch (e: PermissionException) {
                //the list of denied permissions
                repeat(e.denied.size) {

                }
                //the list of forever denied permissions, user has check 'never ask again'
                repeat(e.foreverDenied.size) {

                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun updateMap(location: Location, address: String, zoom: Float?) {
        map?.uiSettings?.isZoomControlsEnabled = true
        map?.isMyLocationEnabled = true

        Timber.d("updateContent")
        val latLng = LatLng(location.latitude, location.longitude)

        zoom?.let { map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, it)) }
            ?: run { map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f)) }

        map?.addMarker(MarkerOptions().position(latLng).draggable(false))?.title = getString(R.string.marker_title)

        this.address.text = address
    }

    override fun showError(e: Throwable) {
        Timber.e(e, "Error: ")

        activity?.let {
            Snackbar.make(it.findViewById(android.R.id.content), "Error: " + e.message, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }

    @ProvidePresenter
    fun providePresenter(): LocationPresenter =
        appComponent().plusLocationComponent(LocationModule()).presenter()
}