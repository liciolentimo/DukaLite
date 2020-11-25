package com.lentimosystems.dukalite.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.lentimosystems.dukalite.R
import com.lentimosystems.dukalite.models.User
import com.lentimosystems.dukalite.utils.Constants
import com.lentimosystems.dukalite.utils.DukaLiteEditText
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException

class UserProfileActivity : BaseActivity(), View.OnClickListener {
    //var et_first_name: DukaLiteEditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        //et_first_name = findViewById(R.id.et_first_name) as DukaLiteEditText

        var userDetails: User = User()
        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        et_first_name!!.isEnabled = false
        et_first_name!!.setText(userDetails.firstName)

        et_last_name!!.isEnabled = false
        et_last_name!!.setText(userDetails.lastName)

        et_email!!.isEnabled = false
        et_email!!.setText(userDetails.email)

        iv_user_photo.setOnClickListener(this@UserProfileActivity)
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
            }
        }
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
                        val selectedImageFileUri = data.data!!
                        iv_user_photo.setImageURI(selectedImageFileUri)
                    } catch (e: IOException){
                        e.printStackTrace()
                        Toast.makeText(this@UserProfileActivity,
                        resources.getString(R.string.image_selection_failed),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}