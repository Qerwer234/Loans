package com.boyko.loans.di

import android.content.Context
import com.boyko.loans.data.repositiry.LoginRepository
import com.boyko.loans.presenter.LoginPresenter
import com.boyko.loans.presenter.LoginPresenterImpl

object LoginPresenterFactory {

    fun create(context: Context): LoginPresenter {
        val loginRepository = LoginRepository(context)

        return LoginPresenterImpl (loginRepository)
    }
}