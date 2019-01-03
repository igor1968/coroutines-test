package com.igordanilchik.coroutinestest.data.source

import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.data.Offers
import com.igordanilchik.coroutinestest.data.source.local.ILocalDataSource
import com.igordanilchik.coroutinestest.data.source.remote.IRemoteDataSource
import com.igordanilchik.coroutinestest.dto.ICatalogueMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import timber.log.Timber

/**
 * @author Igor Danilchik
 */
class Repository(
        private val localDataSource: ILocalDataSource,
        private val remoteDataSource: IRemoteDataSource,
        private val mapper: ICatalogueMapper
) : IRepository {


    override suspend fun getCategories(): ReceiveChannel<Categories> =
        GlobalScope.produce(Dispatchers.Default, capacity = Channel.Factory.CONFLATED, block = {
            val local = localDataSource.categories().await()
            if (local.categories.isNotEmpty()) {
                Timber.d("Produce local")
                send(local)
            }
            val remote = mapper.mapToCategories(remoteDataSource.catalogue().await())
            Timber.d("Produce remote")
            send(remote)
            Timber.d("Save local")
            localDataSource.saveCategories(remote)
        })


    override suspend fun getOffers(): ReceiveChannel<Offers> =
        GlobalScope.produce(Dispatchers.Default, capacity = Channel.Factory.CONFLATED, block = {
            val local = localDataSource.offers().await()
            if (local.offers.isNotEmpty()) {
                Timber.d("Produce local")
                send(local)
            }
            val remote = mapper.mapToOffers(remoteDataSource.catalogue().await())
            Timber.d("Produce remote")
            send(remote)
            Timber.d("Save local")
            localDataSource.saveOffers(remote)
        })

}