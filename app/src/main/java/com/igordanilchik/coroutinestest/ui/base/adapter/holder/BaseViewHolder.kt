package com.igordanilchik.coroutinestest.ui.base.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpDelegate
import kotlinx.android.extensions.LayoutContainer

/**
 * @author Igor Danilchik
 */
abstract class BaseViewHolder<ITEM_TYPE, CALLBACK_TYPE>(
    override val containerView: View,
    private val parentDelegate: MvpDelegate<*>?,
    protected var callback: CALLBACK_TYPE?
) : RecyclerView.ViewHolder(containerView), IBaseViewHolder<ITEM_TYPE>, LayoutContainer {

    private var mvpDelegate: MvpDelegate<out BaseViewHolder<*, *>>? = null

    protected var item: ITEM_TYPE? = null

    /**
     * We can use presenter after call this method
     */
    fun bindView(item: ITEM_TYPE?) {
        this.item = item

        item?.let {
            if (parentDelegate != null) {
                mvpDelegate?.apply {
                    onSaveInstanceState()
                    onDetach()
                    onDestroyView()

                }

                mvpDelegate = getMvpDelegate()
                mvpDelegate?.apply {
                    onCreate()
                    onAttach()
                }
            }

            render(it)
        }
    }

    private fun getMvpDelegate(): MvpDelegate<out BaseViewHolder<*, *>>? =
        mvpDelegate ?: run {
            mvpDelegate = MvpDelegate(this)
            mvpDelegate?.apply { setParentDelegate(parentDelegate, item.hashCode().toString()) }
            return mvpDelegate
        }
}
