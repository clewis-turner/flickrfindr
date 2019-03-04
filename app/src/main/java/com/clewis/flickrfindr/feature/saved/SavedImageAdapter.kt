package com.clewis.flickrfindr.feature.saved

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.clewis.flickrfindr.datamodel.Photo
import com.clewis.flickrfindr.feature.base.ImageCallback


class SavedImageAdapter(private val items: List<Photo>, private val searchCallback: ImageCallback): RecyclerView.Adapter<SavedImageViewHolder>(), ImageCallback by searchCallback {

    override fun onCreateViewHolder(viewGroup: ViewGroup, pos: Int): SavedImageViewHolder {
        return SavedImageViewHolder(LayoutInflater.from(viewGroup.context), viewGroup, this)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(imageViewHolder: SavedImageViewHolder, pos: Int) {
        imageViewHolder.onBind(items[pos])
    }
}
