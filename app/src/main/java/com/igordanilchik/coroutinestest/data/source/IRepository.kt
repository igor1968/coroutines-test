package com.igordanilchik.coroutinestest.data.source

import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.data.Offers
import kotlinx.coroutines.experimental.channels.ReceiveChannel


/**
 * @author Igor Danilchik
 */
interface IRepository {
    suspend fun getCategories(): ReceiveChannel<Categories>
    suspend fun getOffers(): ReceiveChannel<Offers>
}