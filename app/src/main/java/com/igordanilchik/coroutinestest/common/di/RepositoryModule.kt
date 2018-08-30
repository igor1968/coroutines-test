package com.igordanilchik.coroutinestest.common.di

import com.igordanilchik.coroutinestest.data.source.IRepository
import com.igordanilchik.coroutinestest.data.source.Repository
import com.igordanilchik.coroutinestest.data.source.local.ILocalDataSource
import com.igordanilchik.coroutinestest.data.source.remote.IRemoteDataSource
import com.igordanilchik.coroutinestest.dto.ICatalogueMapper
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    internal fun repository(
            localDataSource: ILocalDataSource,
            remoteDataSource: IRemoteDataSource,
            mapper: ICatalogueMapper
    ): IRepository =
            Repository(
                    localDataSource,
                    remoteDataSource,
                    mapper
            )
}
