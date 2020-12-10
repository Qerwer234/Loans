package com.boyko.loans.presenter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.boyko.loans.data.models.Loan
import com.boyko.loans.data.models.LoanRequest
import com.boyko.loans.ui.CreateNewLoan
import com.boyko.loans.ui.Loans

interface LoansPresenter {

    fun attachView(viewLoans: Loans, viewCreateNewLoan: CreateNewLoan)

    fun detachView()

    fun showFragmentLeft(fragment: Fragment, fragmentManager: FragmentManager)

    fun showFragmentRight(fragment: Fragment, fragmentManager: FragmentManager)

    fun showCreateNewLoan(fragmentManager: FragmentManager)

    fun clickToMain(fragmentManager: FragmentManager, context: Context)

    fun showItemLoan(loan: Loan, fragmentManager: FragmentManager)

    fun getAllLoans(context: Context, toast: String)

    fun loanConditionsRequest(context: Context, toast: String)

    fun loanRequest(context: Context, loanRequest: LoanRequest, presenter: LoansPresenter, fragmentManager: FragmentManager, toast: String)

    fun setListLoansToFragment()

    fun getListLoansFromData(): List<Loan>

    fun onItemRequestUpdated( name: String, famaly: String, telephone: String, amount: String, percent: String, period: String )
}