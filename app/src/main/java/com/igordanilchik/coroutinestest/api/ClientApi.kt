package com.igordanilchik.coroutinestest.api


import com.igordanilchik.coroutinestest.dto.inner.Catalogue
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ClientApi {

    @GET("/getyml")
    fun loadCatalogue(@Query("key") key: String): Deferred<Catalogue>

    companion object {

        const val API_BASE_URL = "http://ufa.farfor.ru"
        const val API_KEY = "ukAXxeJYZN"
    }
}
