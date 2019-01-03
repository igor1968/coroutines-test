package com.igordanilchik.coroutinestest.ui.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpDelegate
import com.igordanilchik.coroutinestest.BuildConfig
import com.igordanilchik.coroutinestest.ui.base.adapter.holder.BaseViewHolder
import timber.log.Timber
import java.util.*

/**
 * @author Igor Danilchik
 */

@Suppress("MemberVisibilityCanBePrivate", "unused")
abstract class BaseAdapter<HOLDER : BaseViewHolder<ITEM_TYPE, *>, ITEM_TYPE>(
        items: Collection<ITEM_TYPE>,
        private val parentDelegate: MvpDelegate<*>?
) : RecyclerView.Adapter<HOLDER>() {

    protected abstract val adapterID: String

    private val items = ArrayList<ITEM_TYPE>(items)

    private val mvpDelegate: MvpDelegate<out BaseAdapter<*, *>> by lazy {
        val delegate = MvpDelegate(this)
        parentDelegate?.let {
            delegate.setParentDelegate(it, adapterID)
            delegate.onCreate()
        }
        return@lazy delegate
    }

    private var containerWidth: Int = 0

    private var containerHeight: Int = 0


    override fun onBindViewHolder(holder: HOLDER, position: Int) {
        getItem(position)?.let { holder.bindView(it) }
    }

    /**
     * It appends the given list into the current list. If one item
     * already exists, it updates the content, but not the position.
     */
    fun appendOrUpdate(items: List<ITEM_TYPE>) =
            items.indices.forEach { index -> appendOrUpdate(items[index]) }

    fun appendOrUpdate(item: ITEM_TYPE) {
        val indexOf = items.indexOf(item)
        when {
            indexOf >= 0 -> {
                items[indexOf] = item
                if (hasObservers()) notifyItemChanged(indexOf)
            }
            else -> {
                items += item
                when {
                    hasObservers() -> notifyItemInserted(items.size - 1)
                    else -> if (BuildConfig.DEBUG) Timber.d("Not has observers")

                }
            }
        }
    }

    fun appendOrIgnore(items: List<ITEM_TYPE>) = items.forEach { item -> appendOrIgnore(item) }

    fun appendOrIgnore(item: ITEM_TYPE) {
        if (items.none { it == item }) {
            items += item

            when {
                hasObservers() -> notifyItemInserted(items.indexOf(item))
                else -> if (BuildConfig.DEBUG) Timber.d("Not has observers")
            }
        }
    }

    /**
     * Inserts `item` into passed position `pos`.<br></br>
     * In case position is invalid, item will be inserted in the end of the list.
     *
     * @param pos  position, to be inserted into
     * @param item element to insert
     */
    fun insertInto(pos: Int, item: ITEM_TYPE) =
            when {
                pos < 0 || pos >= itemCount -> appendOrUpdate(item)
                else -> prependOrUpdate(item, pos)
            }

    /**
     * It add the @param items to the current items list. starting
     * in the position 0.
     * If one element already exist into the list, it is moved to the front.
     */
    fun prependOrUpdate(items: List<ITEM_TYPE>) =
            items.indices.forEach { index -> prependOrUpdate(items[index], index) }

    /**
     * It add the @param item to the current items list
     * in the position 0.
     * If the element already exist into the list, it is moved to the front.
     */
    fun prependOrUpdate(item: ITEM_TYPE) = prependOrUpdate(item, 0)

    /**
     * It just replace the item if exist.
     */
    fun update(item: ITEM_TYPE) =
            items.indexOf(item).let { index ->
                items[index] = item
                if (hasObservers()) notifyItemChanged(index)
            }

    private fun prependOrUpdate(item: ITEM_TYPE, endPosition: Int) {
        val currentPosition = items.indexOf(item)
        when {
            currentPosition == endPosition -> {
                items[currentPosition] = item
                if (hasObservers()) notifyItemChanged(currentPosition)
            }
            currentPosition >= 0 -> {
                items.removeAt(currentPosition)
                items.add(endPosition, item)
                if (hasObservers()) notifyItemMoved(currentPosition, endPosition)
            }
            else -> {
                items.add(endPosition, item)
                if (hasObservers()) notifyItemInserted(endPosition)
            }
        }
    }

    fun delete(conversation: ITEM_TYPE) {
        items.indices.forEach { i ->
            when (conversation) {
                items[i] -> {
                    items.removeAt(i)
                    if (hasObservers()) notifyItemRemoved(i)
                    return
                }
                else -> {
                }
            }
        }
    }

    fun replace(freshItem: ITEM_TYPE, currentItem: ITEM_TYPE) {
        val indexOf = items.indexOf(currentItem)
        when {
            indexOf >= 0 -> {
                items[indexOf] = freshItem
                if (hasObservers()) notifyItemChanged(indexOf)
            }
        }
    }

    fun getItem(position: Int): ITEM_TYPE? =
            when {
                position <= items.size -> items[position]
                else -> null
            }

    fun notifyItemsChanged(vararg positions: Int) =
            positions.forEach { pos ->
                notifyItemChanged(pos)
            }

    fun getItems(): List<ITEM_TYPE> = items

    override fun getItemCount(): Int = items.size

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun replaceList(other: Collection<ITEM_TYPE>) {
        items.clear()
        items.addAll(other)
        notifyDataSetChanged()
    }

    fun setContainerSizes(containerWidth: Int, containerHeight: Int) {
        this.containerWidth = containerWidth
        this.containerHeight = containerHeight
    }

    protected fun inflateView(parent: ViewGroup, layoutResID: Int): View =
            LayoutInflater.from(parent.context)
                    .inflate(layoutResID, parent, false)

}
