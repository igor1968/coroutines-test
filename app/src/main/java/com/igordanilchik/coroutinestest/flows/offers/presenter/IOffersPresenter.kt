package com.igordanilchik.coroutinestest.flows.offers.presenter

import com.igordanilchik.coroutinestest.data.Offers

/**
 * @author Igor Danilchik
 */
interface IOffersPresenter {
    fun onOfferClicked(offer: Offers.Offer)
}