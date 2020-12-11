package com.boyko.loans.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.boyko.loans.ActivityLogin
import com.boyko.loans.data.models.LoggedInUser
import com.boyko.loans.ui.Login
import com.boyko.loans.ui.Register

interface LoginPresenter {

    fun attachView(login: Login, register: Register, activityLogin: ActivityLogin)

    fun detachView()

    fun onLoginDataUpdated(username: String, password: String, passwordRepeat: String)

    fun showFragmentLeft(fragment: Fragment, fragmentManager: FragmentManager)

    fun onLoginButtonClicked(context: Context, intent: Intent, activity: Activity, userLoggedInUser: LoggedInUser, s1: String, s2:String, toast:String)

    fun clickToRegistration(fragmentManager: FragmentManager)

    fun clickRegistration(context: Context, intent: Intent, activity: Activity, userLoggedInUser: LoggedInUser, s1: String, s2:String, toast:String)

    fun clickToLogin(fragmentManager: FragmentManager)

    fun isAuth() : Boolean
}