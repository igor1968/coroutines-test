package com.igordanilchik.coroutinestest.flows.catalogue.model

import com.igordanilchik.coroutinestest.data.Categories
import kotlinx.coroutines.channels.ReceiveChannel


/**
 * @author Igor Danilchik
 */
interface ICatalogueModel {
    suspend fun categories(): ReceiveChannel<Categories>
}