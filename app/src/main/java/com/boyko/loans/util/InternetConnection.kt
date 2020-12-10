package com.boyko.loans.util

import android.content.Context
import android.net.ConnectivityManager

class InternetConnection {
    companion object{
        fun checkConnection(context: Context?): Boolean {
            return (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                    .isActiveNetworkMetered
        }
    }

}