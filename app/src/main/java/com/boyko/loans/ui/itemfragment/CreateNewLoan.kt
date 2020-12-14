package com.boyko.loans.ui.itemfragment


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.boyko.loans.R
import com.boyko.loans.data.models.LoanConditions
import com.boyko.loans.data.models.LoanRequest
import com.boyko.loans.presenter.LoansPresenter
import kotlinx.android.synthetic.main.fragment_create_new_loan.*

class CreateNewLoan : Fragment() {

    private var presenter: LoansPresenter? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_new_loan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        context?.let {
            btn_update.setOnClickListener {presenter?.loanConditionsRequest(context!!, getString(R.string.no_connection))}

            presenter?.loanConditionsRequest(context!!, getString(R.string.no_connection))

            btn_send.setOnClickListener { presenter?.loanRequest(
                    context!!,
                    createLoanRequestObject(),
                    presenter!!,
                    fragmentManager!!,
                    getString(R.string.no_connection))
            }
        }
        btn_send.isEnabled = false
    }

    private fun createLoanRequestObject(): LoanRequest {
        return LoanRequest(
                amount = tv_new_amount.text.toString().toInt(),
                firstName = tv_new_first_name.text.toString(),
                lastName = tv_new_last_name.text.toString(),
                percent = tv_new_percent.text.toString().toDouble(),
                period = tv_new_period.text.toString().toInt(),
                phoneNumber = tv_new_phone.text.toString())
    }

    fun setFieldItemLoanFragment(loanConditions: LoanConditions?) {
        tv_new_amount .setText(loanConditions?.maxAmount.toString())
        tv_new_percent.setText(loanConditions?.percent.toString())
        tv_new_period .setText(loanConditions?.period.toString())
    }

    fun showNameError() {
        tv_new_first_name.error = getString(R.string.invalid_name)
    }
    fun showFamalyError() {
        tv_new_last_name.error = getString(R.string.invalid_lastName)
    }
    fun showPhoneError() {
        tv_new_phone.error = getString(R.string.invalid_phone)
    }
    fun showAmountError() {
        tv_new_amount.error = getString(R.string.invalid_amout)
    }
    fun toggleRegButton(enable: Boolean) {
        btn_send.isEnabled = enable
    }
    private fun initViews() {
        tv_new_first_name.afterTextChanged {
            presenter?.onItemRequestUpdated(
                    tv_new_first_name.text.toString(),
                    tv_new_last_name.text.toString(),
                    tv_new_phone.text.toString(),
                    tv_new_amount.text.toString()
            )
        }
        tv_new_first_name.setOnKeyListener { v, keyCode, event ->
            if  (keyCode ==  KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                tv_new_last_name.requestFocus()
            true
        }

        tv_new_last_name.afterTextChanged {
            presenter?.onItemRequestUpdated(
                    tv_new_first_name.text.toString(),
                    tv_new_last_name.text.toString(),
                    tv_new_phone.text.toString(),
                    tv_new_amount.text.toString()
            )
        }
        tv_new_last_name.setOnKeyListener { v, keyCode, event ->
            if  (keyCode ==  KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                tv_new_phone.requestFocus()
            true
        }
        tv_new_phone.afterTextChanged {
            presenter?.onItemRequestUpdated(
                    tv_new_first_name.text.toString(),
                    tv_new_last_name.text.toString(),
                    tv_new_phone.text.toString(),
                    tv_new_amount.text.toString()
            )
        }
        tv_new_phone.setOnKeyListener { v, keyCode, event ->
            if  (keyCode ==  KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                tv_new_amount.requestFocus()
            true
        }
        tv_new_amount.afterTextChanged {
            presenter?.onItemRequestUpdated(
                    tv_new_first_name.text.toString(),
                    tv_new_last_name.text.toString(),
                    tv_new_phone.text.toString(),
                    tv_new_amount.text.toString()
            )
        }
        tv_new_amount.setOnKeyListener { v, keyCode, event ->
            if  (keyCode ==  KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                btn_send.requestFocus()
            true
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(loansPresenter: LoansPresenter) =
            CreateNewLoan().apply {
                presenter = loansPresenter
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