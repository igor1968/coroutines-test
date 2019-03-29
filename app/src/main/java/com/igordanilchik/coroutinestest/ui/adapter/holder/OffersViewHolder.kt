package com.igordanilchik.coroutinestest.ui.adapter.holder

import android.view.View
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.igordanilchik.coroutinestest.R
import com.igordanilchik.coroutinestest.data.Offers
import com.igordanilchik.coroutinestest.data.getParamByKey
import com.igordanilchik.coroutinestest.ui.adapter.OffersAdapter
import com.igordanilchik.coroutinestest.ui.base.adapter.holder.BaseViewHolder
import kotlinx.android.synthetic.main.offers_item.*

/**
 * @author Igor Danilchik
 */
class OffersViewHolder(
    categoryView: View,
    parentDelegate: MvpDelegate<*>?,
    callback: OffersAdapter.OffersCallback?
) : BaseViewHolder<Offers.Offer, OffersAdapter.OffersCallback>(
    categoryView,
    parentDelegate,
    callback

) {

    override fun render(item: Offers.Offer) {
        itemView.setOnClickListener { callback?.onOfferClicked(item) }

        offer_name.text = item.name
        itemView.context.resources?.apply {
            offer_price.text = getString(R.string.offer_price, item.price)
            item.getParamByKey(getString(R.string.param_name_weight)).let {
                offer_weight.text = getString(R.string.offer_weight, it)
            }
        }

        val options = RequestOptions()
            .circleCrop()
            .placeholder(ContextCompat.getDrawable(itemView.context, R.drawable.ic_image_black_24dp))
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(itemView.context)
            .load(item.picture)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(offer_image)
    }
}

