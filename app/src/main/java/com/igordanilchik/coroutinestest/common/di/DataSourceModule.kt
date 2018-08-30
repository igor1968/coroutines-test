package com.igordanilchik.coroutinestest.common.di

import com.igordanilchik.coroutinestest.common.preferences.IAppPreferences
import com.igordanilchik.coroutinestest.data.source.local.ILocalDataSource
import com.igordanilchik.coroutinestest.data.source.local.LocalDataSource
import com.igordanilchik.coroutinestest.data.source.remote.IRemoteDataSource
import com.igordanilchik.coroutinestest.data.source.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

/**
 * @author Igor Danilchik
 */
@Module
class DataSourceModule {

    @Provides
    internal fun local(appPreferences: IAppPreferences): ILocalDataSource = LocalDataSource(appPreferences)

    @Provides
    internal fun remote(httpClient: OkHttpClient): IRemoteDataSource = RemoteDataSource(httpClient)

}