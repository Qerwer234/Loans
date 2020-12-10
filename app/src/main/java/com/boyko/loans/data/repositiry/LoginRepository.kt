package com.boyko.loans.data.repositiry

import android.content.Context
import com.boyko.loans.ActivityLoans

class LoginRepository (val context: Context){

    fun isAuthorized(): Boolean {
        val sharedPref = context.getSharedPreferences(ActivityLoans.PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref?.contains(ActivityLoans.KEY_NAME) == true
    }

    fun getBearer(): String? {
        val sharedPref = context.getSharedPreferences(ActivityLoans.PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref?.getString(ActivityLoans.KEY_NAME, null)
    }

    fun logOut() {
        val sharedPref = context.getSharedPreferences(ActivityLoans.PREFS_NAME, Context.MODE_PRIVATE)
        sharedPref?.edit()?.remove(ActivityLoans.KEY_NAME)?.apply()
    }

    fun authorization(bearer: String?) {
        val editor = context.getSharedPreferences(ActivityLoans.PREFS_NAME, Context.MODE_PRIVATE)?.edit()
        editor?.putString(ActivityLoans.KEY_NAME, bearer)
        editor?.apply()
    }
}