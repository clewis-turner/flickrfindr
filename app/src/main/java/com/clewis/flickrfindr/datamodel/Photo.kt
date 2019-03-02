package com.clewis.flickrfindr.datamodel

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Photo(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    @SerializedName("ispublic") val isPublic: Int,
    @SerializedName("isfriend") val isFriend: Int,
    @SerializedName("isfamily") val isFamily: Int) : Serializable {
    fun getUrl(): String {
        return "https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"
    }
}