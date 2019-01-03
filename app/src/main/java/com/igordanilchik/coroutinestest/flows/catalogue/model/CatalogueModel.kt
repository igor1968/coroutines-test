package com.igordanilchik.coroutinestest.flows.catalogue.model

import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.data.source.IRepository
import com.igordanilchik.coroutinestest.extensions.debounce
import kotlinx.coroutines.channels.ReceiveChannel
import java.util.concurrent.TimeUnit

/**
 * @author Igor Danilchik
 */
class CatalogueModel(private val repository: IRepository) : ICatalogueModel {

    override suspend fun categories(): ReceiveChannel<Categories> =
            repository.getCategories().debounce(400, TimeUnit.MILLISECONDS)

}