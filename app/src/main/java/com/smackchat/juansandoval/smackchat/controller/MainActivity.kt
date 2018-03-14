package com.smackchat.juansandoval.smackchat.controller

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.smackchat.juansandoval.smackchat.R
import com.smackchat.juansandoval.smackchat.services.AuthService
import com.smackchat.juansandoval.smackchat.services.UserDataService
import com.smackchat.juansandoval.smackchat.utils.EXTRA_EMAIL
import com.smackchat.juansandoval.smackchat.utils.EXTRA_USERNAME
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        val userName = intent.getStringExtra(EXTRA_USERNAME)
        val email = intent.getStringExtra(EXTRA_EMAIL)

        userNameNavHeader.text = userName
        userEmailNavHeader.text = email
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (AuthService.isLoggedIn) {
                UserDataService.logout()
                val logutIntent = Intent(this, LoginActivity::class.java)
                startActivity(logutIntent)
                finish()
            }
        }
    }

    fun loginBtnNavHeaderClick(view: View) {
        if (AuthService.isLoggedIn) {
            UserDataService.logout()
            val logutIntent = Intent(this, LoginActivity::class.java)
            startActivity(logutIntent)
            finish()
        }
    }

    fun addChannelClick(view: View) {

    }

    fun sendMessageBtnClick(view: View) {

    }
}
