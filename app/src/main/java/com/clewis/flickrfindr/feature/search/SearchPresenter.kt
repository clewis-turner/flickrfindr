package com.clewis.flickrfindr.feature.search

import android.content.Context
import com.clewis.flickrfindr.datamodel.PhotoData
import com.clewis.flickrfindr.datamodel.PhotoResponse
import com.clewis.flickrfindr.modules.NetworkClientProvider
import com.clewis.flickrfindr.network.FlickrClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import retrofit2.Response


class SearchPresenter(context: Context, private var view: SearchContract.View?): SearchContract.Presenter {

    private val flickrClient: FlickrClient = NetworkClientProvider.flickrClient

    @Volatile private var currentSubscription: Disposable? = null

    private var currentPhotoData: PhotoData? = null
    private var currentSearchTerm: String? = null

    private var recentSearchHelper = RecentSearchHelper(context)

    override fun getCurrentPhotoData(): PhotoData? {
        return currentPhotoData
    }

    override fun retrySearch() {
        val searchTerm = currentSearchTerm ?: return
        onSearch(searchTerm)
    }

    override fun onSearch(searchText: String) {
        recentSearchHelper.onSearch(searchText)
        val subscriber = Consumer<Response<PhotoResponse>> {
            currentPhotoData = it.body()?.photoData
            val photos = currentPhotoData?.photos
            if (photos != null) {
                view?.onSearchResults(photos)
            } else {
                view?.onSearchError()
            }
        }

        val errorConsumer = Consumer<Throwable> {
            view?.onSearchError()
        }

        currentSearchTerm = searchText
        currentSubscription = flickrClient.getImagesForUrl("?method=flickr.photos.search&text=$searchText")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber, errorConsumer)
    }

    override fun onMaxScroll() {
        //Cannot and should not search if we have yet to enter a term or if we have a request outgoing already
        val searchTerm = currentSearchTerm ?: return
        if (currentSubscription != null && currentSubscription?.isDisposed == false) {
            return
        }
        val nextPage = (currentPhotoData?.page ?: return) + 1
        if (nextPage == currentPhotoData?.pages) {
            return
        }

        val subscriber = Consumer<Response<PhotoResponse>> {
            currentPhotoData = it.body()?.photoData
            val photos = currentPhotoData?.photos
            if (photos != null) {
                view?.onAdditionalSearchResults(photos)
            } else {
                //todo - notify?
            }
        }

        val errorConsumer = Consumer<Throwable> {
            photoResponse -> photoResponse.cause
        }

        currentSubscription = flickrClient.getImagesForUrl("?method=flickr.photos.search&page=$nextPage&text=$searchTerm")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber, errorConsumer)

    }

    override fun getRecentSearches(): List<String> {
        return recentSearchHelper.getRecentSearches()
    }

    override fun detach() {
        currentSubscription?.dispose()
        currentSubscription = null
        view = null
    }


}