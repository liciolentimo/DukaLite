package com.lentimosystems.dukalite.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import java.net.URI

object Constants {
    const val USERS: String = "users"
    const val DUKALITE_PREFERENCES: String = "DukaLitePrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val EXTRA_USER_DETAILS: String ="extra_user_details"
    const val READ_STORAGE_PERMISSION_CODE = 201
    const val PICK_IMAGE_REQUEST_CODE = 101
    const val MALE: String = "male"
    const val FEMALE: String = "female"
    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"
    const val USER_PROFILE_IMAGE: String = "user_profile_image"
    const val IMAGE: String = "image"

    fun showImageChooser(activity: Activity){
        val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity,uri: Uri?): String? {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}