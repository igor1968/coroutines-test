package com.igordanilchik.coroutinestest.common.di

import android.app.Application
import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.igordanilchik.coroutinestest.common.factory.FragmentFactory
import com.igordanilchik.coroutinestest.common.factory.ObjectMapperFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val context: Application) {

    @Provides
    @Singleton
    internal fun provideContext(): Context = context.applicationContext

    @Provides
    @Singleton
    internal fun provideApplication(): Application = context

    @Provides
    @Singleton
    internal fun provideObjectMapper(): ObjectMapper = ObjectMapperFactory.newInstance()

    @Provides
    @Singleton
    internal fun provideFragmentFactory(): FragmentFactory = FragmentFactory


}
