package com.clewis.flickrfindr.util;

import android.content.Context;
import android.support.v4.widget.CircularProgressDrawable;

public class ViewUtils {

    public static CircularProgressDrawable getProgressDrawable(Context context) {
        CircularProgressDrawable loadingIndicator = new CircularProgressDrawable(context);
        loadingIndicator.setStrokeWidth(10f);
        loadingIndicator.setCenterRadius(30f);
        return loadingIndicator;
    }
}
