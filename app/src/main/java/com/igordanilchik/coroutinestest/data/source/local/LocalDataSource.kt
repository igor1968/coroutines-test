package com.igordanilchik.coroutinestest.data.source.local

import com.igordanilchik.coroutinestest.common.preferences.IAppPreferences
import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.data.Offers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/**
 * @author Igor Danilchik
 */
class LocalDataSource(private val preferences: IAppPreferences): ILocalDataSource {

    override suspend fun saveCategories(categories: Categories): Deferred<Unit> =
        GlobalScope.async(
            Dispatchers.Default,
            CoroutineStart.DEFAULT
        ) { categories.categories.forEach { category -> preferences.putJSON(KEY_CATEGORY + category.id, category) } }

    override suspend fun saveOffers(offers: Offers): Deferred<Unit> =
        GlobalScope.async(
            Dispatchers.Default,
            CoroutineStart.DEFAULT
        ) { offers.offers.forEach { offer -> preferences.putJSON(KEY_OFFER + offer.id, offer) } }

    override suspend fun categories(): Deferred<Categories> =
        GlobalScope.async(
            Dispatchers.Default,
            CoroutineStart.DEFAULT
        ) { Categories(preferences.getAllObjects(KEY_CATEGORY, Categories.Category::class.java)) }

    override suspend fun offers(): Deferred<Offers> =
        GlobalScope.async(
            Dispatchers.Default,
            CoroutineStart.DEFAULT
        ) { Offers(preferences.getAllObjects(KEY_OFFER, Offers.Offer::class.java)) }

    companion object {
        private const val KEY_CATEGORY = "key_category_"
        private const val KEY_OFFER = "key_offer_"
    }
}