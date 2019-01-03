package com.igordanilchik.coroutinestest.data.source.remote

import com.igordanilchik.coroutinestest.dto.inner.Catalogue
import kotlinx.coroutines.Deferred

/**
 * @author Igor Danilchik
 */
interface IRemoteDataSource {
    suspend fun catalogue(): Deferred<Catalogue>
}