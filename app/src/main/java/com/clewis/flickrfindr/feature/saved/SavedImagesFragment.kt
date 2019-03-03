package com.clewis.flickrfindr.feature.saved

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.clewis.flickrfindr.R
import com.clewis.flickrfindr.datamodel.Photo
import com.clewis.flickrfindr.db.BookmarkManager
import com.clewis.flickrfindr.feature.base.ImageCallback
import com.clewis.flickrfindr.feature.search.SearchImageAdapter


class SavedImagesFragment : Fragment(), ImageCallback {

    companion object {
        const val NAME = "SavedImagesFragment"

        fun newInstance(): SavedImagesFragment {
            return SavedImagesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)

        val bookmarkManager = BookmarkManager(context)

        val imageAdapter = SearchImageAdapter(this)
        imageAdapter.onNewSearch(bookmarkManager.getBookmarks())

        val imageRecyclerView: RecyclerView? = view.findViewById(R.id.saved_recycler_view)
        imageRecyclerView?.adapter = imageAdapter
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        imageRecyclerView?.layoutManager = layoutManager
        imageRecyclerView?.setHasFixedSize(true)

        return view
    }

    override fun onImageClicked(photo: Photo, photoView: ImageView) {
        (activity as ImageCallback?)?.onImageClicked(photo, photoView)
    }

}

