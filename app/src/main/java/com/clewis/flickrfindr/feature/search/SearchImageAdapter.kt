package com.clewis.flickrfindr.feature.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.clewis.flickrfindr.datamodel.Photo
import com.clewis.flickrfindr.feature.base.ImageCallback


class SearchImageAdapter(private val searchCallback: ImageCallback): RecyclerView.Adapter<SearchImageViewHolder>(), ImageCallback by searchCallback {

    var furthestBoundItem = -1
    private val items = ArrayList<Photo>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): SearchImageViewHolder {
        return SearchImageViewHolder(LayoutInflater.from(viewGroup.context), viewGroup, this)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(imageViewHolder: SearchImageViewHolder, pos: Int) {
        imageViewHolder.onBind(items[pos])
        if (pos > furthestBoundItem) {
            furthestBoundItem = pos
        }
    }

    fun addItems(photos: List<Photo>) {
        val position = items.lastIndex
        items.addAll(photos)
        notifyItemRangeInserted(position, photos.size)
    }

    fun onNewSearch(photos: List<Photo>) {
        furthestBoundItem = -1
        items.clear()
        items.addAll(photos)
        notifyDataSetChanged()
    }
}
