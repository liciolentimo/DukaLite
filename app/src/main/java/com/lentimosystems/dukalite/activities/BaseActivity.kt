package com.lentimosystems.dukalite.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.lentimosystems.dukalite.R
import com.lentimosystems.dukalite.utils.DukaLiteEditText
import com.lentimosystems.dukalite.utils.DukaLiteTextView
import com.lentimosystems.dukalite.utils.DukaLiteTextViewBold

open class BaseActivity : AppCompatActivity() {

    private lateinit var mProgressDialog: Dialog
     lateinit var tv_progress_text: DukaLiteTextView

    fun showErrorSnackBar(message: String, errorMessage: Boolean){
        val snackBar = Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage){
            snackBarView.setBackgroundColor(ContextCompat.getColor(this@BaseActivity,R.color.snackBarerror))
        } else{
            snackBarView.setBackgroundColor(ContextCompat.getColor(this@BaseActivity,R.color.green))
        }
        snackBar.show()
    }

    fun showProgressDialog(text: String){
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        //tv_progress_text = findViewById(R.id.tv_progress_text) as DukaLiteTextView
        //mProgressDialog.tv_progress_text.text = text
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }
}