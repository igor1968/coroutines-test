package com.igordanilchik.coroutinestest.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.igordanilchik.coroutinestest.R
import com.igordanilchik.coroutinestest.data.Categories
import com.igordanilchik.coroutinestest.ui.adapter.holder.CategoriesViewHolder
import com.igordanilchik.coroutinestest.ui.base.adapter.BaseAdapter
import com.igordanilchik.coroutinestest.ui.base.adapter.holder.BaseViewHolder

/**
 * @author Igor Danilchik
 */
class CategoriesAdapter(
        categories: Categories,
        private var callback: CategoriesCallback?
): BaseAdapter<BaseViewHolder<Categories.Category, CategoriesAdapter.CategoriesCallback>, Categories.Category>(
        categories.categories,
        null
) {

    override val adapterID: String = CategoriesAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Categories.Category, CategoriesCallback> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return CategoriesViewHolder(v, null, callback)
    }


    interface CategoriesCallback {
        fun onCategoryClicked(category: Categories.Category)
    }
}