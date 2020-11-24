package com.lentimosystems.dukalite.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import com.lentimosystems.dukalite.R
import com.lentimosystems.dukalite.databinding.ActivityLoginBinding
import com.lentimosystems.dukalite.utils.DukaLiteEditText
import com.lentimosystems.dukalite.utils.DukaLiteTextView
import com.lentimosystems.dukalite.utils.DukaLiteTextViewBold

class LoginActivity : BaseActivity(), View.OnClickListener {
    //private lateinit var binding: ActivityLoginBinding
    var tv_register: DukaLiteTextViewBold? = null
    var et_email: DukaLiteEditText? = null
    var et_password: DukaLiteEditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // inflate view binding
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        tv_register = findViewById(R.id.tv_register) as DukaLiteTextViewBold
        et_email = findViewById(R.id.et_email) as DukaLiteEditText
        et_password = findViewById(R.id.et_password) as DukaLiteEditText

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

    }
    override fun onClick(v : View?){
        if (v != null){
            when(v.id){
                R.id.tv_forgot_password -> {

                }
                R.id.btn_login -> {
                    validateLoginDetails()
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
                showErrorSnackBar("Success!",false)
                true
            }
        }
    }
}