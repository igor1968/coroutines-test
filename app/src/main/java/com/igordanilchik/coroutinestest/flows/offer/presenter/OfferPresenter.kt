package com.igordanilchik.coroutinestest.flows.offer.presenter

import com.arellomobile.mvp.InjectViewState
import com.igordanilchik.coroutinestest.common.mvp.presenter.AppBasePresenter
import com.igordanilchik.coroutinestest.flows.offer.model.IOfferModel
import com.igordanilchik.coroutinestest.flows.offer.view.OfferView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch
import timber.log.Timber

/**
 * @author Igor Danilchik
 */
@InjectViewState
class OfferPresenter(
        private val model: IOfferModel
) : AppBasePresenter<OfferView>(), IOfferPresenter {


    override fun attachView(view: OfferView?) {
        super.attachView(view)

        loadData()
    }

    private fun loadData() = launch(UI) {
        viewState.showProgress()

        Timber.d("request offer UI")
        launch(UI) {
            try {
                model.loadOffer().consumeEach {
                    Timber.d("update offer UI")
                    viewState.showOffer(it)
                }
            } catch (e: Throwable) {
                viewState.showError(e)
            } finally {
                viewState.hideProgress()
            }
        }
    }


}