package com.boyko.loans

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.boyko.loans.data.repositiry.LoginRepository
import com.boyko.loans.di.LoginPresenterFactory
import com.boyko.loans.ui.Login
import com.boyko.loans.ui.Register

class LoginActivity: AppCompatActivity(){

    private val loginPresenter  by lazy {  LoginPresenterFactory.create(applicationContext) }
    private val loginRepository by lazy {  LoginRepository(applicationContext) }
    private val mLogin          by lazy {  Login   .newInstance( loginPresenter) }
    private val mRegister       by lazy {  Register.newInstance( loginPresenter) }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPresenter()

        if (loginRepository.isAuthorized()) {
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

}