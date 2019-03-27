package com.example.minimoneybox

import android.content.Context

class MiniMoneyboxSharedPrefs {

    private val prefsKey = "MY_PREFS"
    private val authTokenKey = "AUTH_TOKEN_KEY"
    private val usernameKey = "USERNAME_KEY"

    fun getAuthToken(context: Context): String {
        return context.getSharedPreferences(prefsKey, Context.MODE_PRIVATE).getString(authTokenKey, "")
    }

    fun getUsername(context: Context): String {
        return context.getSharedPreferences(prefsKey, Context.MODE_PRIVATE).getString(usernameKey, "")
    }

    fun setAuthToken(context: Context, authToken: String) {
        val prefs = context.getSharedPreferences(prefsKey, Context.MODE_PRIVATE).edit()
        prefs.putString(authTokenKey, "Bearer $authToken").apply()
    }

    fun setUsername(context: Context, username: String) {
        val prefs = context.getSharedPreferences(prefsKey, Context.MODE_PRIVATE).edit()
        prefs.putString(usernameKey, username).apply()
    }
}