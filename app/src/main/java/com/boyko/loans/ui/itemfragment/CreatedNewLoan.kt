package com.boyko.loans.ui.itemfragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.boyko.loans.R
import com.boyko.loans.data.models.Loan
import com.boyko.loans.presenter.LoansPresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_loan_item.*

// TODO: Rename parameter arguments, choose names that match

private const val ARG_PARAM1 = "param1"
private const val APPROVED = "APPROVED"
private const val REJECTED = "REJECTED"
private const val REGISTERED = "REGISTERED"

class CreatedNewLoan : Fragment() {

    private var param1: String? = null
    private var presenter: LoansPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_created_new_loan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val btn = view?.findViewById<Button>(R.id.btn_to_main)
        btn?.setOnClickListener {
            presenter?.clickToMain(fragmentManager!!, context!!)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFieldItemLoanFragment()
    }

    private fun setFieldItemLoanFragment() {

        val loan = Gson().fromJson(param1, Loan::class.java)
        tv_item_name    .text = loan?.firstName +" "+ loan?.lastName
        tv_item_id      .text = "${loan?.id}"
        tv_item_phone   .text = loan?.phoneNumber
        tv_item_state   .text = loan?.state
        tv_item_amount  .text = loan?.amount.toString()
        tv_item_percent .text = loan?.percent.toString()
        tv_item_period  .text = loan?.period.toString()
        setColorStatus(loan?.state)
    }
    private fun setColorStatus(state: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when(state){
                REGISTERED -> context?.getColor(R.color.item_loan_bg_registred)?.let {
                    tv_item_state.setBackgroundColor(it)
                    tv_item_state1.setBackgroundColor(it)
                }
                APPROVED ->     context?.getColor(R.color.item_loan_bg_aproved)?.let{
                    tv_item_state.setBackgroundColor(it)
                    tv_item_state1.setBackgroundColor(it)
                }
                REJECTED ->     context?.getColor(R.color.item_loan_bg_rejected)?.let{
                    tv_item_state.setBackgroundColor(it)
                    tv_item_state1.setBackgroundColor(it)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, loansPresenter: LoansPresenter) =
                CreatedNewLoan().apply {
                    presenter = loansPresenter
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
}