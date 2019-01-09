package com.igordanilchik.coroutinestest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.igordanilchik.coroutinestest.R
import com.igordanilchik.coroutinestest.data.Offers
import com.igordanilchik.coroutinestest.flows.offers.model.Subcategory
import com.igordanilchik.coroutinestest.ui.adapter.holder.OffersViewHolder
import com.igordanilchik.coroutinestest.ui.base.adapter.BaseAdapter
import com.igordanilchik.coroutinestest.ui.base.adapter.holder.BaseViewHolder

/**
 * @author Igor Danilchik
 */
class OffersAdapter(
        offers: Subcategory,
        private val callback: OffersCallback?
): BaseAdapter<BaseViewHolder<Offers.Offer, OffersAdapter.OffersCallback>, Offers.Offer>(
        offers.meals,
        null
) {

    override val adapterID: String = OffersAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Offers.Offer, OffersCallback> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.offers_item, parent, false)

        return OffersViewHolder(v, null, callback)
    }



    interface OffersCallback {
        fun onOfferClicked(offer: Offers.Offer)
    }
}