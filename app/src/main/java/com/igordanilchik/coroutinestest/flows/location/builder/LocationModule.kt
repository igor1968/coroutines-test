package com.igordanilchik.coroutinestest.flows.location.builder

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.igordanilchik.coroutinestest.flows.location.model.ILocationModel
import com.igordanilchik.coroutinestest.flows.location.model.LocationModel
import com.igordanilchik.coroutinestest.flows.location.presenter.LocationPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
class LocationModule {

    @Provides
    fun presenter(
        model: ILocationModel
    ): LocationPresenter = LocationPresenter(model)

    @Provides
    fun model(
        fusedLocationProvider: FusedLocationProviderClient,
        geocoder: Geocoder
    ): ILocationModel =
        LocationModel(
            fusedLocationProvider,
            geocoder
        )

    @Provides
    fun fusedLocationProvider(context: Context): FusedLocationProviderClient =
        FusedLocationProviderClient(context)

    @Provides
    fun provideGeocoder(context: Context): Geocoder = Geocoder(context)
}