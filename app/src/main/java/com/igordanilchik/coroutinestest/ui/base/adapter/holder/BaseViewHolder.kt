package com.igordanilchik.coroutinestest.ui.base.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpDelegate

/**
 * @author Igor Danilchik
 */
abstract class BaseViewHolder<ITEM_TYPE, CALLBACK_TYPE>(
        itemView: View,
        private val parentDelegate: MvpDelegate<*>?,
        protected var callback: CALLBACK_TYPE?
) : RecyclerView.ViewHolder(itemView), IBaseViewHolder<ITEM_TYPE> {

    private var mvpDelegate: MvpDelegate<out BaseViewHolder<*, *>>? = null

    protected var item: ITEM_TYPE? = null

    init {
        ButterKnife.bind(this, itemView)
    }

    /**
     * We can use presenter after call this method
     */
    fun bindView(item: ITEM_TYPE?) {
        this.item = item

        if (item != null) {
            if (parentDelegate != null) {
                if (mvpDelegate != null) {
                    mvpDelegate!!.onSaveInstanceState()
                    mvpDelegate!!.onDetach()
                    mvpDelegate!!.onDestroyView()
                    mvpDelegate = null
                }

                this.mvpDelegate = getMvpDelegate()

                mvpDelegate!!.onCreate()
                mvpDelegate!!.onAttach()
            }

            render(item)
        }
    }

    protected fun getMvpDelegate(): MvpDelegate<out BaseViewHolder<*, *>>? {
        if (mvpDelegate == null) {
            mvpDelegate = MvpDelegate(this)
            mvpDelegate!!.setParentDelegate(parentDelegate!!, item!!.hashCode().toString())

        }
        return mvpDelegate
    }

}
