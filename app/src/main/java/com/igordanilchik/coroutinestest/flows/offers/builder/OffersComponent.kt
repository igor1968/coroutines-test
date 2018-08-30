package com.igordanilchik.coroutinestest.flows.offers.builder

import com.igordanilchik.coroutinestest.flows.offers.presenter.OffersPresenter
import dagger.Subcomponent

/**
 * @author Igor Danilchik
 */
@Subcomponent(modules = [OffersModule::class])
interface OffersComponent {
    fun presenter(): OffersPresenter
}