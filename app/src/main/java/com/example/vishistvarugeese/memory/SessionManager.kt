package com.example.vishistvarugeese.memory

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * Created by Vishist Varugeese on 27-12-2017.
 */
class SessionManager(private var _context: Context) {

    private var editor: SharedPreferences.Editor
    private var PRIVATE_MODE = 0

    init {
        editor = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE).edit()
    }

    companion object {
        private val TAG = SessionManager::class.java.simpleName
        private const val PREF_NAME = "ONGC_App"
        private const val KEY_IS_LOGGEDIN = "isLoggedIn"
    }

    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn)

        // commit changes
        editor.commit()
        Log.d(TAG, "User login session modified!")
    }

    val isLoggedIn: Boolean get() = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE).getBoolean(KEY_IS_LOGGEDIN, false)




}