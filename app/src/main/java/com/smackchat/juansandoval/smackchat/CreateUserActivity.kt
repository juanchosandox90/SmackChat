package com.smackchat.juansandoval.smackchat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class CreateUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun btnBackToLogin(view: View) {
        val backLoginIntent = Intent(this, LoginActivity::class.java)
        startActivity(backLoginIntent)
    }

    fun createAccountBtnClick(view: View) {

    }

    fun generateUserAvatar(view: View) {

    }
}
