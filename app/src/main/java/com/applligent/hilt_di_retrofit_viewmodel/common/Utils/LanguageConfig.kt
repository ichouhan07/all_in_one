package com.applligent.hilt_di_retrofit_viewmodel.common.Utils

import android.content.Context
import android.content.ContextWrapper
import java.util.*

object LanguageConfig {
    fun changeLanguage(mainContext: Context, languageCode: String?): ContextWrapper {
        var context = mainContext
        val resources = context.resources
        val configuration = resources.configuration
        val systemLocale: Locale =
            configuration.locales[0]
        if (languageCode != "" && systemLocale.language != languageCode) {
            val locale = Locale(languageCode!!)
            Locale.setDefault(locale)
            configuration.setLocale(locale)
            context = context.createConfigurationContext(configuration) }
        return ContextWrapper(context)
    }
}