package com.igordanilchik.coroutinestest.flows.offers.builder

import com.igordanilchik.coroutinestest.data.source.IRepository
import com.igordanilchik.coroutinestest.flows.offers.model.IOffersModel
import com.igordanilchik.coroutinestest.flows.offers.model.OffersModel
import com.igordanilchik.coroutinestest.flows.offers.model.OffersSupplier
import com.igordanilchik.coroutinestest.flows.offers.presenter.OffersPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
class OffersModule(private val supplier: OffersSupplier) {

    @Provides
    fun model(repository: IRepository): IOffersModel =
            OffersModel(
                    repository,
                    supplier
            )

    @Provides
    fun presenter(
            model: IOffersModel
    ): OffersPresenter = OffersPresenter(
            model
    )
}