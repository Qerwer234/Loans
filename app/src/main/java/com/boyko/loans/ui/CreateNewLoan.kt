package com.boyko.loans.ui


import android.os.Bundle
import android.view.*
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
    companion object {
        @JvmStatic
        fun newInstance(loansPresenter: LoansPresenter) =
            CreateNewLoan().apply {
                presenter = loansPresenter
            }
    }
}