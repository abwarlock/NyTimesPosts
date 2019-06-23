package com.abdev.nytimesposts.utils

import android.content.Context

object PrefManager {
    private val PREF_FILE = "APP_PREF"

    fun setValue(context: Context, sectionValue: String) {
        context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
            .edit()
            .putString("section", sectionValue)
            .apply()
    }

    fun getValue(context: Context): String {
        return context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).getString("section", "")
    }
}