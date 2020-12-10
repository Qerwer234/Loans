package com.boyko.loans.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.boyko.loans.ActivityLoans
import com.boyko.loans.LoginActivity
import com.boyko.loans.R
import com.boyko.loans.data.models.LoggedInUser
import com.boyko.loans.presenter.LoginPresenter
import kotlinx.android.synthetic.main.login_fragment.*


class Login : Fragment() {

    var presenterF: LoginPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view:View=inflater.inflate(R.layout.login_fragment,container,false)
        val btnLogin = view.findViewById<Button>(R.id.btn_login)
        val btnReg = view.findViewById<Button>(R.id.btn_register)
        val intent = Intent(context, ActivityLoans::class.java)


        btnLogin.setOnClickListener{
            presenterF?.onLoginButtonClicked(context!!, intent, activity as LoginActivity, userCreate(), getString(R.string.authorization_successful), getString(R.string.error_login), getString(R.string.no_connection))
        }

        btnReg.setOnClickListener{
            fragmentManager?.let { it1 -> presenterF?.clickToRegistration(it1) }
        }
        return view
    }
    private fun userCreate(): LoggedInUser {
        return     LoggedInUser(editText_username.text.toString(),editText_password.text.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance(presenter: LoginPresenter) =
            Login().apply {
                presenterF = presenter
                }
            }
}