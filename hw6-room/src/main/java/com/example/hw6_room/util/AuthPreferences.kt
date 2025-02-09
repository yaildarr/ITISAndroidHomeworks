package com.example.hw6_room.util

import android.content.Context

object AuthPreferences {
    private const val PREF_NAME = "AuthPrefs"
    private const val USER_ID_KEY = "userId"

    fun saveUserId(context: Context, userId: Long) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putLong(USER_ID_KEY, userId)
            .apply()
    }

    fun getUserId(context: Context): Long? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val userId = prefs.getLong(USER_ID_KEY, -1)
        return if (userId == -1L) null else userId
    }

    fun clearUserId(context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .remove(USER_ID_KEY)
            .apply()
    }
}