package com.clewis.flickrfindr.datamodel

import com.google.gson.annotations.SerializedName


data class PhotoResponse(@SerializedName("photos") val photoData: PhotoData);