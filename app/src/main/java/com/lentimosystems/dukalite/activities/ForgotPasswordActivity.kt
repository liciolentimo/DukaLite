package com.lentimosystems.dukalite.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.lentimosystems.dukalite.R

class ForgotPasswordActivity : AppCompatActivity() {
    var toolbar_forgot_password_activity: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        toolbar_forgot_password_activity = findViewById(R.id.toolbar_forgot_password_activity) as Toolbar

        setupActionBar()
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