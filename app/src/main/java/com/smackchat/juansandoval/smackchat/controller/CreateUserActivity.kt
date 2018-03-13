package com.smackchat.juansandoval.smackchat.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast
import com.smackchat.juansandoval.smackchat.R
import com.smackchat.juansandoval.smackchat.services.AuthService
import com.smackchat.juansandoval.smackchat.utils.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvar = "profileDefault"
    var userAvatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createSpinner.visibility = View.INVISIBLE
    }

    fun createAccountBtnClick(view: View) {

        enableSpinner(true)
        val userName = input_username_create.text.toString()
        val email = input_email_create.text.toString()
        val password = input_password_create.text.toString()

        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            AuthService.registerUser(this, email, password) { registerSuccess ->
                if (registerSuccess) {
                    AuthService.loginUser(this, email, password) { loginSuccess ->
                        if (loginSuccess) {
                            AuthService.createUser(this, userName, email, userAvar, userAvatarColor) { createUserSucces ->
                                if (createUserSucces) {
                                    enableSpinner(false)
                                    val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    val mainActivityIntent = Intent(this, MainActivity::class.java)
                                    startActivity(mainActivityIntent)
                                    finish()
                                } else {
                                    errorToast()
                                }
                            }
                        } else {
                            errorToast()
                        }
                    }
                } else {
                    errorToast()
                }
            }
        } else {
            errorFieldsEmpty()
        }
    }

    fun generateUserAvatar(view: View) {
        val random = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        if (color == 0) {
            userAvar = "light$avatar"
        } else {
            userAvar = "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvar, "drawable", packageName)
        createAvatarImageView.setImageResource(resourceId)
    }

    fun enableSpinner(enable: Boolean) {
        if (enable) {
            createSpinner.visibility = View.VISIBLE
        } else {
            createSpinner.visibility = View.INVISIBLE
        }
        btn_signup.isEnabled = !enable
        link_login.isEnabled = !enable
        createAvatarImageView.isEnabled = !enable
    }

    fun errorToast() {
        Toast.makeText(this, getString(R.string.error_toast), Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun errorFieldsEmpty() {
        Toast.makeText(this, getString(R.string.error_fields_empty_toast), Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }
}
