package com.igordanilchik.coroutinestest.dto

import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.data.Offers
import com.igordanilchik.coroutinestest.dto.inner.Catalogue

/**
 * @author Igor Danilchik
 */
interface ICatalogueMapper {
    fun mapToCategories(catalogue: Catalogue): Categories
    fun mapToOffers(catalogue: Catalogue): Offers
}