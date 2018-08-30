package com.igordanilchik.coroutinestest.flows.offers.presenter

import com.arellomobile.mvp.InjectViewState
import com.igordanilchik.coroutinestest.common.mvp.presenter.AppBasePresenter
import com.igordanilchik.coroutinestest.data.Offers
import com.igordanilchik.coroutinestest.flows.offers.model.IOffersModel
import com.igordanilchik.coroutinestest.flows.offers.view.OffersView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch
import timber.log.Timber

/**
 * @author Igor Danilchik
 */
@InjectViewState
class OffersPresenter(
        private val model: IOffersModel
) : AppBasePresenter<OffersView>(), IOffersPresenter {

    override fun attachView(view: OffersView?) {
        super.attachView(view)

        loadData()
    }

    private fun loadData() = launch(UI) {
        viewState.showProgress()

        Timber.d("request offers UI")
        launch(UI) {
            try {
                model.loadOffers().consumeEach {
                    Timber.d("update offers UI")
                    viewState.showOffers(it)
                }
            } catch (e: Throwable) {
                viewState.showError(e)
            } finally {
                viewState.hideProgress()
            }
        }
    }


    override fun onOfferClicked(offer: Offers.Offer) = viewState.goToOffer(offer.id)

}
