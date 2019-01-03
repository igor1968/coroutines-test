package com.igordanilchik.coroutinestest.ui.adapter.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import butterknife.BindView
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

/**
 * @author Igor Danilchik
 */
class OffersViewHolder(
        itemView: View,
        parentDelegate: MvpDelegate<*>?,
        callback: OffersAdapter.OffersCallback?
) : BaseViewHolder<Offers.Offer, OffersAdapter.OffersCallback>(
        itemView,
        parentDelegate,
        callback

) {

    @BindView(R.id.offer_name)
    lateinit var name: TextView
    @BindView(R.id.offer_image)
    lateinit var image: ImageView
    @BindView(R.id.offer_weight)
    lateinit var weight: TextView
    @BindView(R.id.offer_price)
    lateinit var price: TextView


    override fun render(item: Offers.Offer) {
        itemView.setOnClickListener { callback?.onOfferClicked(item) }

        name.text = item.name
        itemView.context.resources ?.apply {
            price.text = getString(R.string.offer_price, item.price)
            item.getParamByKey(getString(R.string.param_name_weight)).let {
                weight.text = getString(R.string.offer_weight, it)
            }
        }

        val options = RequestOptions()
            .fitCenter()
            .centerCrop()
            .placeholder(ContextCompat.getDrawable(itemView.context, R.drawable.ic_image_black_24dp))
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(itemView.context)
            .load(item.picture)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(image)
    }
}

