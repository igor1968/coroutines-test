package com.igordanilchik.coroutinestest.data.source.local

import com.igordanilchik.coroutinestest.common.preferences.IAppPreferences
import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.data.Offers
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

/**
 * @author Igor Danilchik
 */
class LocalDataSource(private val preferences: IAppPreferences): ILocalDataSource {

    override suspend fun saveCategories(categories: Categories): Deferred<Unit> =
            async { categories.categories.forEach { category -> preferences.putJSON(KEY_CATEGORY + category.id, category) } }

    override suspend fun saveOffers(offers: Offers): Deferred<Unit> =
            async { offers.offers.forEach { offer -> preferences.putJSON(KEY_OFFER + offer.id, offer) } }

    override suspend fun categories(): Deferred<Categories> =
            async { Categories(preferences.getAllObjects(KEY_CATEGORY, Categories.Category::class.java)) }

    override suspend fun offers(): Deferred<Offers> =
            async { Offers(preferences.getAllObjects(KEY_OFFER, Offers.Offer::class.java)) }

    companion object {
        private const val KEY_CATEGORY = "key_category_"
        private const val KEY_OFFER = "key_offer_"
    }
}