package com.boyko.loans.data.repositiry

import com.boyko.loans.data.db.LoanDb
import com.boyko.loans.data.models.Loan


class DataRepository ( val dbLoans: LoanDb){

    var listLoans: List<Loan> = listOf()

    fun insert(listLoanCall: List<Loan>) {
        this.listLoans = listLoanCall
        dbLoans.loanDao().insert(listLoanCall)

    }
}