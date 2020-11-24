package com.lentimosystems.dukalite.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.Toolbar
import com.lentimosystems.dukalite.R
import com.lentimosystems.dukalite.utils.DukaLiteButton
import com.lentimosystems.dukalite.utils.DukaLiteEditText
import com.lentimosystems.dukalite.utils.DukaLiteTextViewBold

class RegisterActivity : BaseActivity() {
    var tv_login: DukaLiteTextViewBold? = null
    var toolbar_register_activity: Toolbar? = null
    var et_first_name: DukaLiteEditText? = null
    var et_last_name: DukaLiteEditText? = null
    var et_email: DukaLiteEditText? = null
    var et_password: DukaLiteEditText? = null
    var et_confirm__password: DukaLiteEditText? = null
    var cb_terms_and_conditions: AppCompatCheckBox? = null
    var btn_register: DukaLiteButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_login = findViewById(R.id.tv_login) as DukaLiteTextViewBold
        toolbar_register_activity = findViewById(R.id.toolbar_register_activity) as Toolbar
        et_first_name = findViewById(R.id.et_first_name) as DukaLiteEditText
        et_last_name = findViewById(R.id.et_last_name) as DukaLiteEditText
        btn_register = findViewById(R.id.btn_register) as DukaLiteButton
        et_email = findViewById(R.id.et_email) as DukaLiteEditText
        et_password = findViewById(R.id.et_password) as DukaLiteEditText
        et_confirm__password = findViewById(R.id.et_confirm_password) as DukaLiteEditText
        cb_terms_and_conditions = findViewById(R.id.cb_terms_and_conditions) as AppCompatCheckBox
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        setupActionBar()

        tv_login!!.setOnClickListener{
            val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        btn_register!!.setOnClickListener{
            validateRegisterDetails()
        }
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_register_activity)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }
        toolbar_register_activity?.setNavigationOnClickListener { onBackPressed() }
    }
    private fun validateRegisterDetails(): Boolean{
        return when {
            TextUtils.isEmpty(et_first_name?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name),true)
                false
            }
            TextUtils.isEmpty(et_last_name?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name),true)
                false
            }
            TextUtils.isEmpty(et_email?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
                false
            }
            TextUtils.isEmpty(et_password?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
                false
            }
            TextUtils.isEmpty(et_confirm__password?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password),true)
                false
            }
            et_password?.text.toString().trim{ it <= ' '} != et_confirm__password?.text.toString().trim{ it <= ' '} -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_confirm_password_mismatch),true)
                false
            }
            !cb_terms_and_conditions?.isChecked!! -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_conditions),true)
                false
            } else -> {
                showErrorSnackBar("Success!",false)
                true
            }
        }
    }
}