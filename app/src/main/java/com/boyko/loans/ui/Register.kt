package com.boyko.loans.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.boyko.loans.ActivityLoans
import com.boyko.loans.LoginActivity
import com.boyko.loans.R
import com.boyko.loans.data.models.LoggedInUser
import com.boyko.loans.presenter.LoginPresenter
import kotlinx.android.synthetic.main.registr_fragment.*


class Register : Fragment() {

    var presenterF: LoginPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view:View= inflater.inflate(R.layout.registr_fragment,container,false)

        val btnLogin = view.findViewById<Button>(R.id.btn_reg_login)
        val btnRegRegister = view.findViewById<Button>(R.id.btn_reg_register)

        val intent = Intent(context, ActivityLoans::class.java)

        btnLogin.setOnClickListener{
            fragmentManager?.let { it1 -> presenterF?.clickToLogin(it1) }
        }
        btnRegRegister.setOnClickListener {
            presenterF?.clickRegistration(context!!, intent, activity as LoginActivity, userForReg(),
                getString(R.string.registration_successful),
                getString(R.string.user_already_exist),
                getString(R.string.no_connection))
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }
    private fun userForReg(): LoggedInUser {
        return     LoggedInUser(editText_user_reg.text.toString(),editText_password_reg.text.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance( presenter: LoginPresenter) =
            Register().apply {
                presenterF = presenter
            }
    }

    fun showUsernameError() {
        editText_user_reg.error = getString(R.string.invalid_username)
    }
    fun toggleRegButton(enable: Boolean) {
        btn_reg_register.isEnabled = enable
    }
    fun showPasswordError() {
        editText_password_reg.error = getString(R.string.invalid_password)
    }
    fun showPasswordRepeatError() {
        editText_password_repeat.error = getString(R.string.invalid_repeat_password)
    }
    private fun initViews() {

        editText_user_reg.afterTextChanged {
            presenterF?.onLoginDataUpdated(
                    editText_user_reg.text.toString(),
                    editText_password_reg.text.toString(),
                    editText_password_repeat.text.toString()
            )
        }
        editText_password_reg.afterTextChanged {
            presenterF?.onLoginDataUpdated(
                    editText_user_reg.text.toString(),
                    editText_password_reg.text.toString(),
                    editText_password_repeat.text.toString()
            )
        }
        editText_password_repeat.afterTextChanged {
            presenterF?.onLoginDataUpdated(
                        editText_user_reg.text.toString(),
                        editText_password_reg.text.toString(),
                        editText_password_repeat.text.toString()
                )
            }
    }
}

private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}