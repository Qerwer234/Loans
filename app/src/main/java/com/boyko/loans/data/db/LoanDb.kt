package com.boyko.loans.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.boyko.loans.data.models.Loan

@Database(
    entities = [Loan::class],
    version = 1,
    exportSchema = false
)
abstract class LoanDb : RoomDatabase() {

    abstract fun loanDao(): LoanDao

    companion object {
        private var INSTANCE: LoanDb? = null
        fun getInstance(context: Context): LoanDb {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    LoanDb::class.java,
                    "roomDatabase")
                    .build()
            }
            return INSTANCE as LoanDb
        }
    }
}