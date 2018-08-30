package com.igordanilchik.coroutinestest.flows.offer.builder

import com.igordanilchik.coroutinestest.data.source.IRepository
import com.igordanilchik.coroutinestest.flows.offer.model.IOfferModel
import com.igordanilchik.coroutinestest.flows.offer.model.OfferModel
import com.igordanilchik.coroutinestest.flows.offer.model.OfferSupplier
import com.igordanilchik.coroutinestest.flows.offer.presenter.OfferPresenter
import dagger.Module
import dagger.Provides

/**
 * @author Igor Danilchik
 */
@Module
class OfferModule(private val supplier: OfferSupplier) {

    @Provides
    fun presenter(
            model: IOfferModel
    ): OfferPresenter = OfferPresenter(
            model
    )

    @Provides
    fun model(repository: IRepository): IOfferModel =
            OfferModel(
                    repository,
                    supplier
            )

}