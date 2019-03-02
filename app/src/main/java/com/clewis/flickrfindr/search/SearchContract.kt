package com.clewis.flickrfindr.search

import com.clewis.flickrfindr.datamodel.Photo
import com.clewis.flickrfindr.datamodel.PhotoData


interface SearchContract {

    interface View {
        fun onSearchResults(photos: List<Photo>)

        fun onAdditionalSearchResults(photos: List<Photo>)
    }

    interface Presenter {
        fun onSearch(searchText: String)

        fun onMaxScroll()

        fun getCurrentPhotoData() : PhotoData?

        fun detach()
    }
}