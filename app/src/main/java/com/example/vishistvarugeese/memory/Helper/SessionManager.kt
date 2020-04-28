package com.example.vishistvarugeese.memory.Helper

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * Created by Vishist Varugeese on 27-12-2017.
 */
class SessionManager(var _context: Context) {
    // Shared Preferences
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor

    // Shared pref mode
    var PRIVATE_MODE = 0
    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn)

        // commit changes
        editor.commit()
        Log.d(TAG, "User login session modified!")
    }

    val isLoggedIn: Boolean
        get() = pref.getBoolean(KEY_IS_LOGGEDIN, false)

    companion object {
        // LogCat tag
        private val TAG = SessionManager::class.java.simpleName

        // Shared preferences file name
        private const val PREF_NAME = "ONGC_App"
        private const val KEY_IS_LOGGEDIN = "isLoggedIn"
    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
}