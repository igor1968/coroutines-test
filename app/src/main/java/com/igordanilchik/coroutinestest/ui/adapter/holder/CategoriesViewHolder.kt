package com.igordanilchik.coroutinestest.ui.adapter.holder

import android.view.View
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.MvpDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.igordanilchik.coroutinestest.R
import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.ui.adapter.CategoriesAdapter
import com.igordanilchik.coroutinestest.ui.base.adapter.holder.BaseViewHolder
import kotlinx.android.synthetic.main.category_item.*

/**
 * @author Igor Danilchik
 */
class CategoriesViewHolder(
    containerView: View,
    parentDelegate: MvpDelegate<*>?,
    callback: CategoriesAdapter.CategoriesCallback?
) : BaseViewHolder<Categories.Category, CategoriesAdapter.CategoriesCallback>(
    containerView,
    parentDelegate,
    callback
) {

    override fun render(item: Categories.Category) {
        itemView.setOnClickListener { callback?.onCategoryClicked(item) }

        category_title.text = item.name

        val options = RequestOptions()
            .circleCrop()
            .placeholder(ContextCompat.getDrawable(itemView.context, R.drawable.ic_image_black_24dp))
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(itemView.context)
            .load(item.pictureUrl)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(category_image)
    }
}