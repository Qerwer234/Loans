package com.boyko.loans.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.boyko.loans.R
import com.boyko.loans.adapter.Adapter
import com.boyko.loans.data.models.Loan
import com.boyko.loans.presenter.LoansPresenter
import kotlinx.android.synthetic.main.loans_fragment.*

class Loans : Fragment() {

    lateinit var myAdapter: Adapter
    private var listLoan = listOf<Loan>()

    private var presenter: LoansPresenter? = null
    private var onCreatedFirst = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreatedFirst = true

        myAdapter = Adapter(listLoan, object : Adapter.Callback {
            override fun onItemClicked(item: Loan) {
                presenter?.showItemLoan(item, fragmentManager!!)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.loans_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        serAdapter()

        if (onCreatedFirst) {
            presenter?.getAllLoans(context!!, getString(R.string.no_connection))
            onCreatedFirst = false
        }

        //presenter?.setListLoansToFragment()

        btn_CreateNewLoan.setOnClickListener { presenter?.showCreateNewLoan(fragmentManager!!) }
        fab.setOnClickListener {context?.let { presenter?.getAllLoans(context!!, getString(R.string.no_connection)) }}
    }

    private fun serAdapter() {
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = myAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(loansPresenter: LoansPresenter) =
            Loans().apply {
                presenter = loansPresenter
            }
    }
}