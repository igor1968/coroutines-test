package com.igordanilchik.coroutinestest.ui.adapter.holder

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.arellomobile.mvp.MvpDelegate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.igordanilchik.coroutinestest.R
import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.ui.adapter.CategoriesAdapter
import com.igordanilchik.coroutinestest.ui.base.adapter.holder.BaseViewHolder

/**
 * @author Igor Danilchik
 */
class CategoriesViewHolder(
        itemView: View,
        parentDelegate: MvpDelegate<*>?,
        callback: CategoriesAdapter.CategoriesCallback?
) : BaseViewHolder<Categories.Category, CategoriesAdapter.CategoriesCallback>(
        itemView,
        parentDelegate,
        callback
) {

    @BindView(R.id.category_title)
    lateinit var title: TextView
    @BindView(R.id.category_image)
    lateinit var icon: ImageView

    override fun render(item: Categories.Category) {
        itemView.setOnClickListener { callback?.onCategoryClicked(item) }

        title.text = item.name

        Glide.with(itemView.context)
                .load(item.pictureUrl)
                .fitCenter()
                .centerCrop()
                .placeholder(ContextCompat.getDrawable(itemView.context, R.drawable.ic_image_black_24dp))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(icon)
    }
}