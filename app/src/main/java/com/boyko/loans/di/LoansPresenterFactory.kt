package com.boyko.loans.di

import android.content.Context
import com.boyko.loans.data.db.LoanDb
import com.boyko.loans.data.repositiry.DataRepository
import com.boyko.loans.data.repositiry.LoginRepository
import com.boyko.loans.presenter.LoansPresenter
import com.boyko.loans.presenter.LoansPresenterImpl

object LoansPresenterFactory {

    fun create(context: Context): LoansPresenter {

        val loginRepository = LoginRepository(context)
        val dbLoans = LoanDb.getInstance(context)
        val dataRepository = DataRepository(dbLoans)

        return LoansPresenterImpl(loginRepository, dataRepository)
    }
}