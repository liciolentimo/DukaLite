package com.lentimosystems.dukalite.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.lentimosystems.dukalite.R
import com.lentimosystems.dukalite.utils.Constants
import com.lentimosystems.dukalite.utils.DukaLiteTextViewBold

class MainActivity : AppCompatActivity() {
    var tv_main: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_main = findViewById(R.id.tv_main) as TextView

        val sharedPreferences = getSharedPreferences(Constants.DUKALITE_PREFERENCES,Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME,"")!!
        tv_main!!.text = "Hello $username"
    }
}