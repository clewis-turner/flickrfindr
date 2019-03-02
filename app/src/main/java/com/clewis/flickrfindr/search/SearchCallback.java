package com.clewis.flickrfindr.search;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.clewis.flickrfindr.datamodel.Photo;

public interface SearchCallback {

    void onImageClicked(@NonNull Photo photo, @NonNull ImageView photoView);

    void onImageSaved(@NonNull Photo photo);
}
