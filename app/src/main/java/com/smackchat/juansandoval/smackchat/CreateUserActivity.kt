package com.smackchat.juansandoval.smackchat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvar = "profileDefault"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }


    fun createAccountBtnClick(view: View) {

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
}
