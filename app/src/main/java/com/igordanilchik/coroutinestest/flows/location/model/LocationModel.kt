package com.igordanilchik.coroutinestest.flows.location.model

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit


/**
 * @author Igor Danilchik
 */
class LocationModel(
        private val fusedLocationProvider: FusedLocationProviderClient,
        private val geocoder: Geocoder
) : ILocationModel {

    companion object {
        private val LOCATION_TIMEOUT_IN_SECONDS = TimeUnit.SECONDS.toMillis(5)
        private val LOCATION_UPDATE_INTERVAL = TimeUnit.SECONDS.toMillis(60)
        private val SUFFICIENT_ACCURACY = 500f
        private val MAX_ADDRESSES = 1
    }

    private val channel by lazy { Channel<Location>(capacity = Channel.CONFLATED) }


    private val callback: LocationCallback =  object: LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            Timber.d("Location produced")
            channel.offer(result.lastLocation)
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun location(): ReceiveChannel<Location> =
            channel.also { channel ->
                launch {
                    Timber.d("Last Location produced")
                    channel.send(Tasks.await(fusedLocationProvider.lastLocation))

                    val req = LocationRequest.create()
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setExpirationDuration(LOCATION_TIMEOUT_IN_SECONDS)
                            .setInterval(LOCATION_UPDATE_INTERVAL)

                    fusedLocationProvider.requestLocationUpdates(req, callback, Looper.getMainLooper())
                }
            }
//                    .filter { location -> location.accuracy < SUFFICIENT_ACCURACY }
//                    .debounce(LOCATION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)

//    @SuppressLint("MissingPermission")
//    override suspend fun location(): ReceiveChannel<Location> =
//            produce(capacity = Channel.CONFLATED) {
//                send(lastLocationRead())
//                send(locationRead())
//            }
////                    .filter { location -> location.accuracy < SUFFICIENT_ACCURACY }
////                    .debounce(LOCATION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
//
//
//    @SuppressLint("MissingPermission")
//    suspend fun lastLocationRead(): Location =
//            suspendCoroutine { cont ->
//                fusedLocationProvider.lastLocation.addOnSuccessListener{ location ->
//                    location?.let {
//                        Timber.d("Last Location produced")
//                        cont.resume(it)
//                    }
//                }
//            }
//
//
//    @SuppressLint("MissingPermission")
//    suspend fun locationRead(): Location =
//            suspendCoroutine { cont ->
//                val req = LocationRequest.create()
//                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                        .setExpirationDuration(LOCATION_TIMEOUT_IN_SECONDS)
//                        .setInterval(LOCATION_UPDATE_INTERVAL)
//
//                fusedLocationProvider.requestLocationUpdates(req, object : LocationCallback() {
//                    override fun onLocationResult(result: LocationResult) {
//                        Timber.d("Location produced")
//                        cont.resume(result.lastLocation)
//                    }
//                }, Looper.getMainLooper())
//            }



    override suspend fun address(location: Location): String =
            geocoder.getFromLocation(location.latitude, location.longitude, MAX_ADDRESSES).firstOrNull()?.let { address ->

                val addressString = StringBuilder()
                for (i in 0..address.maxAddressLineIndex) {
                    addressString.append(address.getAddressLine(i)).append(" ")
                }
                return@let addressString.toString()
            } ?: "Address unknown"

    override fun dispose() {
        fusedLocationProvider.removeLocationUpdates(callback)
    }

}