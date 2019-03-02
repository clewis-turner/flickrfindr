package com.clewis.flickrfindr.search;

import android.support.annotation.NonNull;

import com.clewis.flickrfindr.datamodel.Photo;

public interface SearchCallback {

    void onImageClicked(@NonNull Photo photo);

    void onImageSaved(@NonNull Photo photo);
}
