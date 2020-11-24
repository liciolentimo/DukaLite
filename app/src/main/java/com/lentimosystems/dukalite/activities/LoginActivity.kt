package com.lentimosystems.dukalite.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import com.lentimosystems.dukalite.R
import com.lentimosystems.dukalite.databinding.ActivityLoginBinding
import com.lentimosystems.dukalite.utils.DukaLiteTextView
import com.lentimosystems.dukalite.utils.DukaLiteTextViewBold

class LoginActivity : AppCompatActivity() {
    //private lateinit var binding: ActivityLoginBinding
    var tv_register: DukaLiteTextViewBold? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // inflate view binding
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        tv_register = findViewById(R.id.tv_register) as DukaLiteTextViewBold

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
}