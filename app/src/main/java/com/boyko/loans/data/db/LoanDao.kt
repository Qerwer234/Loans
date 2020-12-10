package com.boyko.loans.data.db

import androidx.room.*
import com.boyko.loans.data.models.Loan

@Dao
interface LoanDao {

    @Query("SELECT * from loan_database")
    fun getAll(): List<Loan>

    @Query("SELECT * FROM loan_database WHERE id = :id")
    fun findById(id: Int): Loan

    @Query("DELETE FROM loan_database")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listLoan: List<Loan>)

    @Update
    fun update(loan: Loan)

    @Delete
    fun delete(loan: Loan)
}