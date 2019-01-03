package com.igordanilchik.coroutinestest.flows.offers.model

import com.igordanilchik.coroutinestest.data.Offers
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * @author Igor Danilchik
 */
interface IOffersModel {
    suspend fun loadOffers(): ReceiveChannel<Offers>
}