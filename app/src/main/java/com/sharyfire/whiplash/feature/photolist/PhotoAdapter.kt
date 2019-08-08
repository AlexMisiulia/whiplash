package com.sharyfire.whiplash.feature.photolist

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sharyfire.whiplash.R
import com.sharyfire.whiplash.feature.shared.EndlessAdapter
import com.sharyfire.whiplash.utils.inflaterAdapterView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.photo_item_view.*

private const val TAG = "PhotoAdapter"

class PhotoAdapter(private val onClick: (DisplayablePhoto) -> Unit) :
    EndlessAdapter<DisplayablePhoto, PhotoAdapter.PhotoViewHolder>(
) {
    override fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(parent.inflaterAdapterView(R.layout.photo_item_view))
    }

    override fun onBindContentViewHolder(holder: PhotoViewHolder, item: DisplayablePhoto) {
        holder.bind(item)
    }

    inner class PhotoViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            containerView.setOnClickListener {
                onClick(getContentItem(adapterPosition))
            }
        }

        fun bind(item: DisplayablePhoto) {
            Glide.with(containerView.context).load(item.url).into(imageView)
        }

    }
}