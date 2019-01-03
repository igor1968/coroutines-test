package com.igordanilchik.coroutinestest.data.source.local

import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.data.Offers
import kotlinx.coroutines.Deferred

/**
 * @author Igor Danilchik
 */
interface ILocalDataSource {
    suspend fun saveCategories(categories: Categories): Deferred<Unit>
    suspend fun saveOffers(offers: Offers): Deferred<Unit>
    suspend fun categories(): Deferred<Categories>
    suspend fun offers(): Deferred<Offers>
}