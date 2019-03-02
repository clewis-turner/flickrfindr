package com.clewis.flickrfindr.network;

import com.clewis.flickrfindr.datamodel.PhotoResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface FlickrClient {

    @GET
    Observable<Response<PhotoResponse>> getImagesForUrl(@Url String url);

}