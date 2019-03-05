package com.clewis.flickrfindr.feature.search

import com.clewis.flickrfindr.datamodel.Photo
import com.clewis.flickrfindr.datamodel.PhotoData


interface SearchContract {

    interface View {
        fun onSearchError()

        fun onSearchResults(photos: List<Photo>)

        fun onAdditionalSearchResults(photos: List<Photo>)
    }

    interface Presenter {
        fun onSearch(searchText: String)

        fun retrySearch()

        fun onMaxScroll()

        fun getCurrentPhotoData() : PhotoData?

        fun detach()
    }
}