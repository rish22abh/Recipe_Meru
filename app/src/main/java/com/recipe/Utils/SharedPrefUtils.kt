package com.recipe.Utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefUtils {
    companion object {

        const val LOGIN_MAIL = "login_mail"

        private fun getPref(context: Context): SharedPreferences? {
            return context.getSharedPreferences(
                "meru_share",
                Context.MODE_PRIVATE
            )
        }

        fun getString(context: Context,key: String?, defaultValue: String?): String? {
            return getPref(context)!!.getString(key, defaultValue)
        }

        fun putString(context: Context,key: String?, value: String?) {
            val editor = getPref(
                context
            )!!.edit()
            editor.putString(key, value)
            editor.apply()
        }
    }
}