package com.igordanilchik.coroutinestest.flows.offer.model

import com.igordanilchik.coroutinestest.data.Offers
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * @author Igor Danilchik
 */
interface IOfferModel {
    suspend fun loadOffer(): ReceiveChannel<Offers.Offer>
}