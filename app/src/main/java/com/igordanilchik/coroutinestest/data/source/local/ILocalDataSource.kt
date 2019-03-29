package com.igordanilchik.coroutinestest.data.source.local

import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.data.Offers
import kotlinx.coroutines.Deferred

/**
 * @author Igor Danilchik
 */
interface ILocalDataSource {
    suspend fun saveCategoriesAsync(categories: Categories): Deferred<Unit>
    suspend fun saveOffersAsync(offers: Offers): Deferred<Unit>
    suspend fun categoriesAsync(): Deferred<Categories>
    suspend fun offersAsync(): Deferred<Offers>
}