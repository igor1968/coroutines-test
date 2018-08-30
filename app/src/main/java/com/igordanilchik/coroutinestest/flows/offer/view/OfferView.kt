package com.igordanilchik.coroutinestest.flows.offer.view

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.igordanilchik.coroutinestest.common.mvp.view.AppBaseView
import com.igordanilchik.coroutinestest.data.Offers
import com.igordanilchik.coroutinestest.flows.offer.router.OfferRouter

/**
 * @author Igor Danilchik
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface OfferView: AppBaseView, OfferRouter {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showOffer(offer: Offers.Offer)
    fun showProgress()
    fun hideProgress()
    fun showError(throwable: Throwable)
}