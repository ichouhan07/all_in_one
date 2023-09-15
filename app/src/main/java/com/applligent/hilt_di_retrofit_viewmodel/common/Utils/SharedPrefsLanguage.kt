package com.applligent.hilt_di_retrofit_viewmodel.common.Utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsLanguage(var context: Context) {
    var localeKey = "locale"
    var setbackLocale = "en"
    var databaseName = "database"
    var editor: SharedPreferences.Editor =
        context.getSharedPreferences(databaseName, Context.MODE_PRIVATE).edit()
    var preferences: SharedPreferences = context.getSharedPreferences(databaseName, Context.MODE_PRIVATE)

    var locale: String?
        get() = if (preferences.contains(localeKey)) {
            preferences.getString(localeKey, "")
        } else {
            setbackLocale
        }
        set(lang) {
            editor.putString(localeKey, lang)
            editor.apply()
        }
}