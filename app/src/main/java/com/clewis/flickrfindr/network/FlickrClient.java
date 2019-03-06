package com.clewis.flickrfindr.network;

import com.clewis.flickrfindr.datamodel.PhotoResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrClient {

    @GET("?method=flickr.photos.search")
    Observable<Response<PhotoResponse>> getImages(@Query("text") String search);

    @GET("?method=flickr.photos.search")
    Observable<Response<PhotoResponse>> getImages(@Query("text") String search, @Query("page") int nextPage);

}