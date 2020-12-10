package com.boyko.loans.data.models

data class LoanRequest(
    val amount : Int,
    val firstName : String,
    val lastName : String,
    val percent : Double,
    val period : Int,
    val phoneNumber : String
)
