package com.igordanilchik.coroutinestest.ui.base.adapter.holder

/**
 * @author Igor Danilchik
 */
interface IBaseViewHolder<ITEM_TYPE> {

    /**
     * Init section in this method
     */
    fun render(item: ITEM_TYPE)
}