package com.smackchat.juansandoval.smackchat.services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.smackchat.juansandoval.smackchat.utils.*
import org.json.JSONException
import org.json.JSONObject


object AuthService {

    var isLoggedIn = false
    var userEmail = ""
    var authToken = ""
    val tagError: String = "ERROR"

    fun registerUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit) {

        val jsonBody = JSONObject()
        jsonBody.put(EXTRA_EMAIL, email)
        jsonBody.put(EXTRA_PASSWORD, password)
        val requestBody = jsonBody.toString()

        val registerRequest = object : StringRequest(Method.POST, URL_REGISTER, Response.Listener { response ->
            println(response)
            complete(true)
        }, Response.ErrorListener { error ->
            Log.d(tagError, "$error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return JSON_APPLICATION_TYPE
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(registerRequest)
    }

    fun loginUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit) {

        val jsonBody = JSONObject()
        jsonBody.put(EXTRA_EMAIL, email)
        jsonBody.put(EXTRA_PASSWORD, password)
        val requestBody = jsonBody.toString()

        val loginRequest = object : JsonObjectRequest(Method.POST, URL_LOGIN, null, Response.Listener { response ->

            try {
                userEmail = response.getString(EXTRA_USER)
                authToken = response.getString(EXTRA_TOKEN)
                isLoggedIn = true
                complete(true)
            } catch (e: JSONException) {
                Log.d(tagError, e.localizedMessage)
                complete(false)
            }

        }, Response.ErrorListener { error ->
            Log.d(tagError, "$error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return JSON_APPLICATION_TYPE
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(loginRequest)
    }

    fun createUser(context: Context, name: String, email: String, avatarName: String, avatarColor: String, complete: (Boolean) -> Unit) {

        val jsonBody = JSONObject()
        jsonBody.put(EXTRA_NAME, name)
        jsonBody.put(EXTRA_EMAIL, email)
        jsonBody.put(EXTRA_AVATAR_NAME, avatarName)
        jsonBody.put(EXTRA_AVATAR_COLOR, avatarColor)
        val requestBody = jsonBody.toString()

        val createUserRequest = object : JsonObjectRequest(Method.POST, URL_CREATE_USER, null, Response.Listener { response ->

            try {
                UserDataService.name = response.getString(EXTRA_NAME)
                UserDataService.email = response.getString(EXTRA_EMAIL)
                UserDataService.avatarName = response.getString(EXTRA_AVATAR_NAME)
                UserDataService.avatarColor = response.getString(EXTRA_AVATAR_COLOR)
                UserDataService.id = response.getString(EXTRA_ID)
                complete(true)
            } catch (e: JSONException) {
                Log.d(tagError, e.localizedMessage)
                complete(false)
            }

        }, Response.ErrorListener { error ->
            Log.d(tagError, "$error")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return JSON_APPLICATION_TYPE
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put(EXTRA_AUTHORIZATION, EXTRA_BEARER + authToken)
                return headers
            }
        }

        Volley.newRequestQueue(context).add(createUserRequest)
    }

}