package com.lentimosystems.dukalite.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.lentimosystems.dukalite.R
import com.lentimosystems.dukalite.firestore.FireStoreClass
import com.lentimosystems.dukalite.models.User
import com.lentimosystems.dukalite.utils.Constants
import com.lentimosystems.dukalite.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException

class UserProfileActivity : BaseActivity(), View.OnClickListener {
    private lateinit var iv_user_photo: ImageView
    private lateinit var mUserDetails: User
    private var mSelectedImageFileUri: Uri? = null
    private var mUSerProfileImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        iv_user_photo = findViewById(R.id.iv_user_photo) as ImageView


        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        et_first_name!!.isEnabled = false
        et_first_name!!.setText(mUserDetails.firstName)

        et_last_name!!.isEnabled = false
        et_last_name!!.setText(mUserDetails.lastName)

        et_email!!.isEnabled = false
        et_email!!.setText(mUserDetails.email)

        iv_user_photo.setOnClickListener(this@UserProfileActivity)
        btn_submit.setOnClickListener(this@UserProfileActivity)
    }

    override fun onClick(v: View?) {
        if (v != null){
            when(v.id){
                R.id.iv_user_photo -> {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ){
                        //showErrorSnackBar("You have already allowed this permission",false)
                        Constants.showImageChooser(this)
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }

                R.id.btn_submit -> {

                    if (validateUserProfileDetails()){
                        //showErrorSnackBar("Details updated successfully!",false)
                        showProgressDialog(resources.getString(R.string.please_wait))
                        if (mUSerProfileImageURL != null)
                        FireStoreClass().uploadImageToCloudStorage(this, mSelectedImageFileUri)
                        else {
                            updateUserProfileDetails()
                        }
                    }
                }
            }
        }
    }

    private fun updateUserProfileDetails(){
        val userHashMap = HashMap<String,Any>()
        val mobileNumber = et_mobile_number.text.toString().trim { it <= ' ' }
        val gender = if (rb_male.isChecked){
            Constants.MALE
        } else {
            Constants.FEMALE
        }
        if (mUSerProfileImageURL.isNotEmpty()){
            userHashMap[Constants.IMAGE] = mUSerProfileImageURL
        }
        if (mobileNumber.isNotEmpty()){
            userHashMap[Constants.MOBILE] = mobileNumber.toLong()
        }
        userHashMap[Constants.GENDER] = gender

        //showProgressDialog(resources.getString(R.string.please_wait))

        FireStoreClass().updateUserProfileData(this, userHashMap)
    }

    fun userProfileUpdateSuccess(){
        hideProgressDialog()
        Toast.makeText(this@UserProfileActivity,resources.getString(R.string.msg_profile_update_success),Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@UserProfileActivity,MainActivity::class.java))
        finish()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //showErrorSnackBar("Storage permission granted", false)
                Constants.showImageChooser(this)
            } else {
                Toast.makeText(this,resources.getString(R.string.read_storage_permission_denied),Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (resultCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null){
                    try {
                        mSelectedImageFileUri = data.data!!
                        //iv_user_photo.setImageURI(selectedImageFileUri)
                        GlideLoader(this).loadUserPicture(mSelectedImageFileUri!!, iv_user_photo)
                    } catch (e: IOException){
                        e.printStackTrace()
                        Toast.makeText(this@UserProfileActivity,
                        resources.getString(R.string.image_selection_failed),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else if(resultCode == Activity.RESULT_CANCELED){
            Log.e("Request cancelled","Image selection cancelled")
        }
    }
    private fun validateUserProfileDetails(): Boolean{
        return when {
            TextUtils.isEmpty(et_mobile_number.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number),true)
                false
            } else -> {
                true
            }
        }
    }

    fun imageUploadSuccess(imageURL: String){
        //hideProgressDialog()
//        Toast.makeText(this@UserProfileActivity,
//        "Image uploaded successfully! Image uURL is $imageURL",Toast.LENGTH_SHORT).show()
        mUSerProfileImageURL = imageURL
        updateUserProfileDetails()
    }
}