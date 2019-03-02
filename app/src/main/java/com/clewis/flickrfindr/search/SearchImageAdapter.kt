package com.clewis.flickrfindr.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.clewis.flickrfindr.datamodel.Photo


class SearchImageAdapter(private val searchCallback: SearchCallback): RecyclerView.Adapter<SearchViewHolder>(), SearchCallback by searchCallback {

    var furthestBoundItem = -1
    private val items = ArrayList<Photo>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(viewGroup.context), viewGroup, this)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(searchViewHolder: SearchViewHolder, pos: Int) {
        searchViewHolder.onBind(items[pos])
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
