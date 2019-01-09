package com.igordanilchik.coroutinestest.flows.offers.model

import com.igordanilchik.coroutinestest.data.source.IRepository
import com.igordanilchik.coroutinestest.extensions.debounce
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.map
import java.util.concurrent.TimeUnit

/**
 * @author Igor Danilchik
 */
class OffersModel(
    private val repository: IRepository,
    private val supplier: OffersSupplier
) : IOffersModel {

    private val id get() = supplier.id

    private val name get() = supplier.name

    override suspend fun loadSubcategory(): ReceiveChannel<Subcategory> =
        repository.getOffers()
            .debounce(400, TimeUnit.MILLISECONDS)
            .map { offers -> offers.offers.filter { offer -> offer.categoryId == id } }
            .map { offers ->
                Subcategory(
                    categoryId = id,
                    categoryName = name,
                    meals = offers
                )
            }
}