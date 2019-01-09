package com.igordanilchik.coroutinestest.flows.offer.presenter

import com.arellomobile.mvp.InjectViewState
import com.igordanilchik.coroutinestest.common.mvp.presenter.AppBasePresenter
import com.igordanilchik.coroutinestest.flows.offer.model.IOfferModel
import com.igordanilchik.coroutinestest.flows.offer.view.OfferView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author Igor Danilchik
 */
@ObsoleteCoroutinesApi
@InjectViewState
class OfferPresenter(
        private val model: IOfferModel
) : AppBasePresenter<OfferView>(), IOfferPresenter {


    override fun attachView(view: OfferView?) {
        super.attachView(view)

        loadData()
    }

    private fun loadData() = GlobalScope.launch(Dispatchers.Main) {
        viewState.showProgress()

        Timber.d("request offer UI")
        GlobalScope.launch(Dispatchers.Main) {
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