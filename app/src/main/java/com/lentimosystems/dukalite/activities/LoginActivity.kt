package com.lentimosystems.dukalite.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.lentimosystems.dukalite.R
import com.lentimosystems.dukalite.databinding.ActivityLoginBinding
import com.lentimosystems.dukalite.firestore.FireStoreClass
import com.lentimosystems.dukalite.models.User
import com.lentimosystems.dukalite.utils.DukaLiteButton
import com.lentimosystems.dukalite.utils.DukaLiteEditText
import com.lentimosystems.dukalite.utils.DukaLiteTextView
import com.lentimosystems.dukalite.utils.DukaLiteTextViewBold

class LoginActivity : BaseActivity(), View.OnClickListener {
    //private lateinit var binding: ActivityLoginBinding
    var tv_register: DukaLiteTextViewBold? = null
    var et_email: DukaLiteEditText? = null
    var et_password: DukaLiteEditText? = null
    var tv_forgot_password: DukaLiteTextView? = null
    var btn_login: DukaLiteButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // inflate view binding
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        tv_register = findViewById(R.id.tv_register) as DukaLiteTextViewBold
        tv_forgot_password = findViewById(R.id.tv_forgot_password) as DukaLiteTextView
        et_email = findViewById(R.id.et_email) as DukaLiteEditText
        et_password = findViewById(R.id.et_password) as DukaLiteEditText
        btn_login = findViewById(R.id.btn_login) as DukaLiteButton

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
tv_register!!.setOnClickListener{
    val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
    startActivity(intent)
}
        tv_forgot_password!!.setOnClickListener(this)
        btn_login!!.setOnClickListener(this)

    }

    fun userLoggedInSuccess(user: User){
        hideProgressDialog()

        Log.i("First Name: ", user.firstName)
        Log.i("Last Name: ", user.lastName)
        Log.i("Email: ", user.email)

        val intent = Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onClick(v : View?){
        if (v != null){
            when(v.id){
                R.id.tv_forgot_password -> {
                    val intent = Intent(this@LoginActivity,ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }
                R.id.btn_login -> {
                    loginRegisteredUser()
                }
            }
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when{
            TextUtils.isEmpty(et_email?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
                false
            }
            TextUtils.isEmpty(et_password?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
                false
            } else -> {
                //showErrorSnackBar("Success!",false)
                true
            }
        }
    }
    private fun loginRegisteredUser(){
        if (validateLoginDetails()){
            showProgressDialog(resources.getString(R.string.please_wait))
            val email: String = et_email?.text.toString().trim{it <= ' '}
            val password: String = et_password?.text.toString().trim{it <= ' '}

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {task ->
                    //hideProgressDialog()

                    if (task.isSuccessful){
                        FireStoreClass().getUserDetails(this@LoginActivity)
                        //showErrorSnackBar("Success!",false)
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }
                }
        }
    }
}