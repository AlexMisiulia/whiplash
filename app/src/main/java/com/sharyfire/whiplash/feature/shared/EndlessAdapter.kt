package com.sharyfire.whiplash.feature.shared

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.sharyfire.whiplash.R
import com.sharyfire.whiplash.utils.inflaterAdapterView

interface AdapterItem
object LoadingFooter : AdapterItem
object ErrorFooter : AdapterItem

private const val LOADING_FOOTER_TYPE = 1
private const val ERROR_FOOTER_TYPE = 2
private const val CONTENT_TYPE = 3

private val diffUtilCallback = object: DiffUtil.ItemCallback<AdapterItem>() {
    override fun areItemsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean {
        return oldItem === newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: AdapterItem, newItem: AdapterItem): Boolean {
        return oldItem == newItem
    }
}

private const val TAG = "EndlessAdapter"

abstract class EndlessAdapter<Item: AdapterItem, Holder: RecyclerView.ViewHolder> :
    ListAdapter<AdapterItem, RecyclerView.ViewHolder>(diffUtilCallback) {

    private val items = ArrayList<AdapterItem>()

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            LoadingFooter -> LOADING_FOOTER_TYPE
            ErrorFooter -> ERROR_FOOTER_TYPE
            else -> CONTENT_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder")

        return when(viewType) {
            LOADING_FOOTER_TYPE -> LoadingFooterViewHolder(parent.inflaterAdapterView(R.layout.loading_footer_item))
            ERROR_FOOTER_TYPE -> ErrorFooterViewHolder(parent.inflaterAdapterView(R.layout.error_footer_item))
            CONTENT_TYPE -> onCreateContentViewHolder(parent, viewType)
            else -> throw IllegalArgumentException("Unknown view type = $viewType")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")

        when(holder) {
            is LoadingFooterViewHolder -> holder.bind()
            is ErrorFooterViewHolder -> holder.bind()
            else -> onBindContentViewHolder(holder as Holder, getItem(position) as Item)
        }
    }

    abstract fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int) : Holder
    abstract fun onBindContentViewHolder(holder: Holder, item: Item)

    @Suppress("UNCHECKED_CAST")
    fun getContentItem(position: Int) : Item {
        return getItem(position) as Item
    }

    fun setError(enableError: Boolean) {
        Log.d(TAG, "setLoading=$enableError")

        val isError = isError()

        if(enableError) {
            if(!isError) modifyItems { add(ErrorFooter) }
        } else {
            if(isError) modifyItems{ remove(ErrorFooter) }
        }
    }

    fun setLoading(enableLoading: Boolean) {
        Log.d(TAG, "setLoading=$enableLoading")

        val isLoading = isLoading()

        if(enableLoading) {
            if(!isLoading) modifyItems { add(LoadingFooter) }
        } else {
            if(isLoading) modifyItems { remove(LoadingFooter) }
        }
    }

    fun isLoading() = getLastItem() is LoadingFooter
    private fun isError() = getLastItem() is ErrorFooter

    private fun getLastItem(): AdapterItem? {
        return if(itemCount != 0) {
            getItem(itemCount - 1)
        } else {
            null
        }
    }

    private fun modifyItems(block: ArrayList<AdapterItem>.() -> Unit) {
        val updatedItems = ArrayList(items)
        block(updatedItems)
        Log.d(TAG, "modifyItems, submit list =$updatedItems)")
        submitListInternal(updatedItems)
    }

    fun submitContentList(items: List<Item>) {
        val wasLoading = isLoading()
        val wasError = isError()

        val adapterItems = ArrayList<AdapterItem>()
        adapterItems.addAll(items)
        if(wasLoading) adapterItems.add(LoadingFooter)
        if(wasError) adapterItems.add(LoadingFooter)

        Log.d(TAG, "submitContentList=${adapterItems.size})")
        submitListInternal(adapterItems)
    }

    private fun submitListInternal(items: List<AdapterItem>) {
        this.items.clear()
        this.items.addAll(items)

        submitList(items)
    }

    class LoadingFooterViewHolder(v: View): RecyclerView.ViewHolder(v) {
        fun bind() {

        }
    }

    class ErrorFooterViewHolder(v: View): RecyclerView.ViewHolder(v) {
        fun bind() {

        }
    }
}