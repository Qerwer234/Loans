package com.boyko.loans.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loan_database")
data class Loan (
    @PrimaryKey val id: Int?,
    val firstName : String,
    val lastName : String,
    val phoneNumber : String,
    val amount : Int,
    val percent : Double,
    val period : Int,
    val date : String,
    val state : String,
)
