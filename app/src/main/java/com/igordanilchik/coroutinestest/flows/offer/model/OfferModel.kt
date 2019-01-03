package com.igordanilchik.coroutinestest.flows.offer.model

import com.igordanilchik.coroutinestest.data.Offers
import com.igordanilchik.coroutinestest.data.source.IRepository
import com.igordanilchik.coroutinestest.extensions.debounce
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
import java.util.concurrent.TimeUnit

/**
 * @author Igor Danilchik
 */
class OfferModel(
        private val repository: IRepository,
        private val supplier: OfferSupplier
) : IOfferModel {

    private val id get() = supplier.id

    override suspend fun loadOffer(): ReceiveChannel<Offers.Offer> =
            repository.getOffers()
                    .debounce(400, TimeUnit.MILLISECONDS)
                    .map { offers -> offers.offers.first { it.id == id } }

}