package com.clewis.flickrfindr.feature.base;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.clewis.flickrfindr.datamodel.Photo;

public interface ImageCallback {

    void onImageClicked(@NonNull Photo photo, @NonNull ImageView photoView);
}
