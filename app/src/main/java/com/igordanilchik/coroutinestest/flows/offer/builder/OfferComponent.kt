package com.igordanilchik.coroutinestest.flows.offer.builder

import com.igordanilchik.coroutinestest.flows.offer.presenter.OfferPresenter
import dagger.Subcomponent

/**
 * @author Igor Danilchik
 */
@Subcomponent(modules = [OfferModule::class])
interface OfferComponent {
    fun presenter(): OfferPresenter
}