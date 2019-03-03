package com.clewis.flickrfindr.db

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.clewis.flickrfindr.datamodel.Photo
import com.google.gson.Gson


class BookmarkManager(context: Context?) {

    private var photos: List<Photo>? = null
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        const val KEY_PHOTO_IDS = "KEY_PHOTO_IDS"

        fun photoKey(id: String) = "KEY_PHOTO_$id"
    }


    fun isPhotoBookmarked(photo: Photo?): Boolean {
        if (photo == null) {
            return false
        }
        return sharedPreferences.getStringSet(KEY_PHOTO_IDS, HashSet<String>())?.contains(photo.id) == true
    }

    fun getBookmarks(): List<Photo> {
        val photos = ArrayList<Photo>()

        val photoKeys = sharedPreferences.getStringSet(KEY_PHOTO_IDS, HashSet<String>())
        photoKeys?.forEach {
            val photo = getPhoto(it) ?: return@forEach //local return, we failed to retrieve this image somehow
            photos.add(photo)
        }
        return photos
    }

    fun savePhoto(photo: Photo) {
        val photoKeys = sharedPreferences.getStringSet(KEY_PHOTO_IDS, HashSet<String>()) ?: HashSet<String>()
        photoKeys.add(photo.id)
        sharedPreferences.edit().putStringSet(KEY_PHOTO_IDS, photoKeys).apply()

        val gson = Gson()
        sharedPreferences.edit().putString(photoKey(photo.id), gson.toJson(photo)).apply()
    }

    fun deletePhoto(photo: Photo) {
        val photoKeys = sharedPreferences.getStringSet(KEY_PHOTO_IDS, HashSet<String>()) ?: HashSet<String>()
        photoKeys.remove(photo.id)
        sharedPreferences.edit().putStringSet(KEY_PHOTO_IDS, photoKeys).apply()

        sharedPreferences.edit().remove(photoKey(photo.id)).apply()
    }

    private fun getPhoto(id: String): Photo? {
        val gson = Gson()
        val photoJson = sharedPreferences.getString(photoKey(id), null)
        if (photoJson != null) {
            return gson.fromJson(photoJson, Photo::class.java)
        }

        return null
    }

}