package com.clewis.flickrfindr.datamodel

import com.google.gson.annotations.SerializedName


data class PhotoData(
    val page: Int,
    val pages: Int,
    val total: Int,
    @SerializedName("perpage") val perPage: Int,
    @SerializedName("photo") val photos: List<Photo>
)