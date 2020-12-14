package com.boyko.loans

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.boyko.loans.di.LoginPresenterFactory
import com.boyko.loans.ui.Login
import com.boyko.loans.ui.Register


open class ActivityLogin: AppCompatActivity(){

    private val loginPresenter  by lazy {  LoginPresenterFactory.create(applicationContext) }
    private val mLogin          by lazy {  Login   .newInstance(loginPresenter) }
    private val mRegister       by lazy {  Register.newInstance(loginPresenter) }
    private val sharedPrefs     by lazy {  getSharedPreferences(ActivityLoans.PREFS_NAME, Context.MODE_PRIVATE) }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initTheme()
        initPresenter()

        if (loginPresenter.isAuth()) {
            startActivity(Intent(this, ActivityLoans::class.java))
            finish()
        } else loginPresenter.showFragmentLeft(mLogin, supportFragmentManager)
    }

    private fun initPresenter() {
        loginPresenter.attachView(mLogin, mRegister, this)
    }

    override fun onBackPressed() {
        when (supportFragmentManager.findFragmentById(R.id.main_container)) {
            is Register -> loginPresenter.showFragmentLeft(mLogin, supportFragmentManager)
            is Login -> finish()
            else -> super.onBackPressed()
        }
    }

    override fun onDestroy() {
        loginPresenter.detachView()
        super.onDestroy()
    }

    private fun initTheme() {
        when (getSavedTheme()) {
            THEME_LIGHT -> setTheme(AppCompatDelegate.MODE_NIGHT_NO, THEME_LIGHT)
            THEME_DARK -> setTheme(AppCompatDelegate.MODE_NIGHT_YES, THEME_DARK)
            THEME_UNDEFINED -> setTheme(AppCompatDelegate.MODE_NIGHT_NO, THEME_LIGHT)
        }
    }

    private fun getSavedTheme() = sharedPrefs.getInt(KEY_THEME, THEME_UNDEFINED)
    private fun saveTheme(theme: Int) = sharedPrefs.edit().putInt(KEY_THEME, theme).apply()
    private fun setTheme(themeMode: Int, prefsMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        saveTheme(prefsMode)
    }
}