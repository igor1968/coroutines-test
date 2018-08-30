package com.igordanilchik.coroutinestest.flows.catalogue.builder

import com.igordanilchik.coroutinestest.data.source.IRepository
import com.igordanilchik.coroutinestest.flows.catalogue.model.CatalogueModel
import com.igordanilchik.coroutinestest.flows.catalogue.model.ICatalogueModel
import com.igordanilchik.coroutinestest.flows.catalogue.presenter.CataloguePresenter
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
class CatalogueModule {

    @Provides
    internal fun presenter(
            model: ICatalogueModel
    ): CataloguePresenter = CataloguePresenter(
            model
    )

    @Provides
    internal fun model(repository: IRepository): ICatalogueModel = CatalogueModel(repository)

}