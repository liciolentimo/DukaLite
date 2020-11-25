package com.lentimosystems.dukalite.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lentimosystems.dukalite.R
import com.lentimosystems.dukalite.models.User
import com.lentimosystems.dukalite.utils.Constants
import com.lentimosystems.dukalite.utils.DukaLiteEditText
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {
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
    }
}