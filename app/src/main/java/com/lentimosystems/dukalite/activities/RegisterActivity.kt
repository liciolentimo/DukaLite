package com.lentimosystems.dukalite.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import com.lentimosystems.dukalite.R
import com.lentimosystems.dukalite.utils.DukaLiteTextViewBold

class RegisterActivity : AppCompatActivity() {
    var tv_login: DukaLiteTextViewBold? = null
    var toolbar_register_activity: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_login = findViewById(R.id.tv_login) as DukaLiteTextViewBold
        toolbar_register_activity = findViewById(R.id.toolbar_register_activity) as Toolbar

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
}