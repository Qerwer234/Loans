package com.boyko.loans.ui.itemfragment

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.boyko.loans.R
import com.boyko.loans.data.models.Loan
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_loan_item.*
import kotlinx.android.synthetic.main.fragment_loan_item.tv_item_amount
import kotlinx.android.synthetic.main.fragment_loan_item.tv_item_id
import kotlinx.android.synthetic.main.fragment_loan_item.tv_item_name
import kotlinx.android.synthetic.main.fragment_loan_item.tv_item_percent
import kotlinx.android.synthetic.main.fragment_loan_item.tv_item_period
import kotlinx.android.synthetic.main.fragment_loan_item.tv_item_phone
import kotlinx.android.synthetic.main.fragment_loan_item.tv_item_state
import kotlinx.android.synthetic.main.fragment_loan_item.tv_item_state1

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val APPROVED = "APPROVED"
private const val REJECTED = "REJECTED"
private const val REGISTERED = "REGISTERED"

class LoanItem : Fragment() {
    // TODO: Rename and change types of parameters
    private var data: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_loan_item, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFieldItemLoanFragment()
    }

    private fun setFieldItemLoanFragment() {
        var loan = Gson().fromJson(data, Loan::class.java)
        tv_item_name.text = "${loan?.firstName} ${loan?.lastName} "
        tv_item_phone.text = loan?.phoneNumber
        tv_item_amount.text = loan?.amount.toString()
        tv_item_percent.text = loan?.percent.toString()
        tv_item_period.text = loan?.period.toString()
        tv_item_id.text = dataTostring(loan)

        setColorStatus(loan?.state)
    }

    private fun setColorStatus(state: String?) {
        when (state) {
            REGISTERED -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    context?.getColor(R.color.item_loan_bg_registred)?.let {
                        tv_item_state.setBackgroundColor(it)
                        tv_item_state1.setBackgroundColor(it)
                    }
                }

                tv_item_state.text = getString(R.string.status_REGISTRED)
                tv_item_instruction.text = getString(R.string.if_loan_registred)
            }
            APPROVED -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    context?.getColor(R.color.item_loan_bg_aproved)?.let {
                        tv_item_state.setBackgroundColor(it)
                        tv_item_state1.setBackgroundColor(it)
                    }
                }

                tv_item_state.text = getString(R.string.status_APPROVED)
                tv_item_instruction.text = getString(R.string.if_loan_aproved)
            }
            REJECTED -> {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    context?.getColor(R.color.item_loan_bg_rejected)?.let {
                        tv_item_state.setBackgroundColor(it)
                        tv_item_state1.setBackgroundColor(it)
                    }
                }
                tv_item_state.text = getString(R.string.status_REJECTED)
                tv_item_instruction.text = getString(R.string.if_loan_rejected)
            }

        }
    }

    private fun dataTostring(loan: Loan): CharSequence? {
        val data: String? = loan.date
        val list = data?.split("T",":","-")
        var s = ""
        list?.let {
             s = "№ ${loan.id} от ${list.get(2)}.${list.get(1)}.${list.get(0)}" +
                     "\n${list.get(3)}:${list.get(4)}"
        }
        return s
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
                LoanItem().apply {
                     arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
}