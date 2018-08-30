package com.igordanilchik.coroutinestest.common.di

import com.igordanilchik.coroutinestest.common.mvp.view.BaseFragment
import com.igordanilchik.coroutinestest.flows.catalogue.builder.CatalogueComponent
import com.igordanilchik.coroutinestest.flows.catalogue.builder.CatalogueModule
import com.igordanilchik.coroutinestest.flows.location.builder.LocationComponent
import com.igordanilchik.coroutinestest.flows.location.builder.LocationModule
import com.igordanilchik.coroutinestest.flows.offer.builder.OfferComponent
import com.igordanilchik.coroutinestest.flows.offer.builder.OfferModule
import com.igordanilchik.coroutinestest.flows.offers.builder.OffersComponent
import com.igordanilchik.coroutinestest.flows.offers.builder.OffersModule
import com.igordanilchik.coroutinestest.ui.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    HttpClientModule::class,
    RepositoryModule::class,
    DataSourceModule::class,
    AppPreferencesModule::class,
    MapperModule::class
])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(baseFragment: BaseFragment)

    fun plusCatalogueComponent(catalogueModule: CatalogueModule): CatalogueComponent
    fun plusOffersComponent(offersModule: OffersModule): OffersComponent
    fun plusOfferComponent(offerModule: OfferModule): OfferComponent
    fun plusLocationComponent(locationModule: LocationModule): LocationComponent
}
