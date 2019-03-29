package com.igordanilchik.coroutinestest.flows.offers.view

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import com.igordanilchik.coroutinestest.R
import com.igordanilchik.coroutinestest.common.mvp.view.BaseFragment
import com.igordanilchik.coroutinestest.data.Offers
import com.igordanilchik.coroutinestest.flows.offers.builder.OffersModule
import com.igordanilchik.coroutinestest.flows.offers.model.OffersSupplier
import com.igordanilchik.coroutinestest.flows.offers.model.Subcategory
import com.igordanilchik.coroutinestest.flows.offers.presenter.OffersPresenter
import com.igordanilchik.coroutinestest.ui.adapter.OffersAdapter
import kotlinx.android.synthetic.main.fragment_offers.*

/**
 * @author Igor Danilchik
 */
class OffersFragment : BaseFragment(), OffersView, OffersAdapter.OffersCallback {

    @InjectPresenter
    lateinit var presenter: OffersPresenter

    override val layoutResID = R.layout.fragment_offers

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        offers_recycler_view.setHasFixedSize(true)
        offers_recycler_view.layoutManager = LinearLayoutManager(activity)
        offers_recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onDestroyView() {
        offers_recycler_view.adapter = null

        super.onDestroyView()
    }

    override fun onOfferClicked(offer: Offers.Offer) = presenter.onOfferClicked(offer)

    override fun showOffers(subcategory: Subcategory) {
        (offers_recycler_view.adapter as? OffersAdapter)?.apply {
            appendOrUpdate(subcategory.meals)
        } ?: run {
            offers_recycler_view.adapter = OffersAdapter(subcategory, this)
        }

        subcategory.categoryName?.let { setTitle(it) }
    }

    override fun showError(throwable: Throwable) =
        Snackbar.make(offers_recycler_view, "Error: " + throwable.message, Snackbar.LENGTH_LONG)
            .show()

    override fun showProgress() {
        swipe_container.post { swipe_container.isRefreshing = true }
    }

    override fun hideProgress() {
        swipe_container.post { swipe_container.isRefreshing = false }
    }

    override fun goToOffer(id: Int) {
        val directions = OffersFragmentDirections.toOfferFragment().setOfferId(id)
        view?.findNavController()?.navigate(directions)
    }

    @ProvidePresenter
    fun providePresenter(): OffersPresenter {
        val supplier = OffersSupplier(
            id = arguments?.let { OffersFragmentArgs.fromBundle(it).categoryId } ?: 0,
            name = arguments?.let { OffersFragmentArgs.fromBundle(it).categoryName }
        )

        return appComponent().plusOffersComponent(OffersModule(supplier)).presenter()
    }
}