package com.igordanilchik.coroutinestest.data.source.remote

import com.igordanilchik.coroutinestest.api.ClientApi
import com.igordanilchik.coroutinestest.dto.inner.Catalogue
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

/**
 * @author Igor Danilchik
 */
class RemoteDataSource(httpClient: OkHttpClient): IRemoteDataSource {

    private val restApi: ClientApi =
            Retrofit.Builder()
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .baseUrl(ClientApi.API_BASE_URL)
                    .client(httpClient)
                    .build()
                    .create(ClientApi::class.java)


    override suspend fun catalogue(): Deferred<Catalogue> =
            restApi.loadCatalogue(ClientApi.API_KEY)



}