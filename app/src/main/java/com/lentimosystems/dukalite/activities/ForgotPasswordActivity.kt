package com.lentimosystems.dukalite.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.lentimosystems.dukalite.R
import com.lentimosystems.dukalite.utils.DukaLiteButton
import com.lentimosystems.dukalite.utils.DukaLiteEditText

class ForgotPasswordActivity : BaseActivity() {
    var toolbar_forgot_password_activity: Toolbar? = null
    var btn_submit: DukaLiteButton? = null
    var et_email_forgot: DukaLiteEditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        toolbar_forgot_password_activity = findViewById(R.id.toolbar_forgot_password_activity) as Toolbar
        btn_submit = findViewById(R.id.btn_submit) as DukaLiteButton
        et_email_forgot = findViewById(R.id.et_email_forgot) as DukaLiteEditText

        setupActionBar()

        btn_submit!!.setOnClickListener{
            val email: String = et_email_forgot!!.text.toString().trim{it<= ' '}
            if (email.isEmpty()){
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
            } else {
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        hideProgressDialog()
                        if (task.isSuccessful){
                            Toast.makeText(this@ForgotPasswordActivity,
                            resources.getString(R.string.email_sent_success),Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(),true)
                        }
                    }
            }
        }
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_forgot_password_activity)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        toolbar_forgot_password_activity?.setNavigationOnClickListener { onBackPressed() }
    }
}