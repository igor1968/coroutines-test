package com.igordanilchik.coroutinestest.flows.offers.model

import kotlinx.coroutines.channels.ReceiveChannel

/**
 * @author Igor Danilchik
 */
interface IOffersModel {
    suspend fun loadSubcategory(): ReceiveChannel<Subcategory>
}