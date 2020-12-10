package com.boyko.loans.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class Client {

    companion object {
        private const val ROOT_URL = "http://focusapp-env.eba-xm2atk2z.eu-central-1.elasticbeanstalk.com"
        private val builder = GsonBuilder()
                .setLenient()
                .create()
        private val retrofitInstance: Retrofit
        get() = Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(builder))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val apiService: ApiService
            get() = retrofitInstance.create(ApiService::class.java)
        }
}