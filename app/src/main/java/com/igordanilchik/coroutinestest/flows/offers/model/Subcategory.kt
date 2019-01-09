package com.igordanilchik.coroutinestest.flows.offers.model

import com.igordanilchik.coroutinestest.data.Offers

/**
 * @author Igor Danilchik
 */
data class Subcategory(
    val meals: List<Offers.Offer>,
    val categoryId: Int,
    val categoryName: String?
)
