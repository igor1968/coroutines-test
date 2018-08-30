package com.igordanilchik.coroutinestest.data.source

import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.data.Offers
import com.igordanilchik.coroutinestest.data.source.local.ILocalDataSource
import com.igordanilchik.coroutinestest.data.source.remote.IRemoteDataSource
import com.igordanilchik.coroutinestest.dto.ICatalogueMapper
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.produce
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
            produce(capacity = Channel.Factory.CONFLATED) {
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
            }


    override suspend fun getOffers(): ReceiveChannel<Offers> =
            produce(capacity = Channel.Factory.CONFLATED) {
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
            }

}