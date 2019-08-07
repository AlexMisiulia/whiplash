package com.sharyfire.whiplash.feature.photolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sharyfire.whiplash.R
import com.sharyfire.whiplash.entity.ui.DisplayablePhoto
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.photo_item_view.*

private val diffUtilCallback = object: DiffUtil.ItemCallback<DisplayablePhoto>() {
    override fun areItemsTheSame(oldItem: DisplayablePhoto, newItem: DisplayablePhoto): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: DisplayablePhoto, newItem: DisplayablePhoto): Boolean {
        return oldItem == newItem
    }

}

class PhotoAdapter : ListAdapter<DisplayablePhoto, PhotoAdapter.PhotoViewHolder>(
    diffUtilCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item_view, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PhotoViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(item: DisplayablePhoto) {
            Picasso.get().load(item.url).into(imageView)
        }

    }
}