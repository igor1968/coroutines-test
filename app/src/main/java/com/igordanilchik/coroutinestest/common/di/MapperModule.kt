package com.igordanilchik.coroutinestest.common.di

import com.igordanilchik.coroutinestest.dto.CatalogueMapper
import com.igordanilchik.coroutinestest.dto.ICatalogueMapper
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
class MapperModule {

    @Provides
    fun mapper(): ICatalogueMapper = CatalogueMapper()

}