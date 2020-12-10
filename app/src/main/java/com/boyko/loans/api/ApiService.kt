package com.boyko.loans.api

import com.boyko.loans.data.models.*
import io.reactivex.Observable

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    fun postLogin(
            @Header ("accept") accept: String,
            @Header ("Content-Type") type: String,
            @Body user: LoggedInUser
    ): Observable<String>
    @POST("/registration")
    fun postReg(
            @Header ("accept") accept: String,
            @Header ("Content-Type") type: String,
            @Body user: LoggedInUser): Observable<UserEntity>
    @POST("/loans")
    fun postGetLoans(
            @Header ("accept") accept: String,
            @Header ("Authorization") auth: String,
            @Body loanRequest: LoanRequest
    ): Observable<Loan>

    @GET("/loans/conditions")
    fun getLoansConditions(
            @Header ("accept") accept: String,
            @Header ("Authorization") auth: String) : Observable<LoanConditions>

    @GET("/loans/all")
    fun getLoansAll(
            @Header ("accept") accept: String,
            @Header ("Authorization") auth: String) : Observable<List<Loan>>
}