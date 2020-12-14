package com.boyko.loans.presenter

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.boyko.loans.ActivityLoans
import com.boyko.loans.R
import com.boyko.loans.adapter.Adapter
import com.boyko.loans.api.Client
import com.boyko.loans.data.models.Loan
import com.boyko.loans.data.models.LoanConditions
import com.boyko.loans.data.models.LoanRequest
import com.boyko.loans.data.repositiry.DataRepository
import com.boyko.loans.data.repositiry.LoginRepository
import com.boyko.loans.errors.ErrorsMake
import com.boyko.loans.ui.Loans
import com.boyko.loans.ui.itemfragment.CreateNewLoan
import com.boyko.loans.ui.itemfragment.CreatedNewLoan
import com.boyko.loans.ui.itemfragment.LoanItem
import com.boyko.loans.util.InternetConnection
import com.google.gson.Gson
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.loans_fragment.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern.compile


class LoansPresenterImpl(private val loginRepository: LoginRepository, private val dataRepository: DataRepository) :
    LoansPresenter {

    private var mLoans           : Loans? = null
    private var mCreateNewLoan   : CreateNewLoan? = null
    private var errors = ErrorsMake()

    private val api = Client.apiService

    override fun attachView(
            viewLoans: Loans
    ) {
        this.mLoans  = viewLoans
    }

    override fun detachView() {
        this.mLoans  = null
    }
    override fun showFragmentLeft(fragment: Fragment, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(R.anim.left_in, R.anim.left_out)
            .replace(R.id.main_loans_container, fragment, fragment.javaClass.name)
            .commit()
    }
    override fun showFragmentRight(fragment: Fragment, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(R.anim.right_in, R.anim.right_out)
            .replace(R.id.main_loans_container, fragment, fragment.javaClass.name)
            .commit()
    }

    override fun showCreateNewLoan(fragmentManager: FragmentManager) {
        mCreateNewLoan = CreateNewLoan.newInstance(this)

        this.mCreateNewLoan?.let { showFragmentLeft(it, fragmentManager) }
    }

    override fun clickToMain(fragmentManager: FragmentManager, context: Context) {
        mLoans?.let { showFragmentRight(it,fragmentManager)}
        doAsync { uiThread {getAllLoans(context, context.getString(R.string.no_connection)) } }
    }

    override fun showItemLoan(loan: Loan, fragmentManager: FragmentManager) {
        showFragmentLeft(LoanItem.newInstance(Gson().toJson(loan)),fragmentManager)
    }

    private fun isConnect(context: Context): Boolean{
        return InternetConnection.checkConnection(context)
    }

    override fun getAllLoans(context: Context, toast: String) {
        if (isConnect(context)) {
            loginRepository.getBearer()?.let {
                mLoans?.progressBar?.visibility = View.VISIBLE
                val call = api.getLoansAll(ActivityLoans.ACCEPT, it)
                call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .timeout(7, TimeUnit.SECONDS)
                    .subscribe(object : Observer<List<Loan>> {
                        override fun onSubscribe(d: Disposable) {}
                        override fun onNext(listLoanCall: List<Loan>) {
                            mLoans?.apply {
                                progressBar?.visibility = View.INVISIBLE
                                myAdapter.update(listLoanCall)
                                recyclerview?.smoothScrollToPosition(0)
                            }
                            doAsync {
                                dataRepository.insert(listLoanCall)
                                uiThread {
                                    Toast.makeText(
                                            context,
                                            context.getString(R.string.count_loans) + " " + listLoanCall.size.toString(),
                                            Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }

                        override fun onError(e: Throwable) {
                            mLoans?.progressBar?.visibility = View.INVISIBLE
                            toastErrors(context, errors.errorToString(e.message.toString()))
                            setListLoansToFragment()
                            toastErrors(context, errors.errorToString(context.resources.getString(R.string.uploaded_history)))
                        }

                        override fun onComplete() {}
                    })
            }
        } else {
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            setListLoansToFragment()
            Toast.makeText(context, context.resources.getString(R.string.uploaded_history), Toast.LENGTH_LONG).show()
        }
    }

    private fun toastErrors(context: Context, e: String) {
        val mainHandler = android.os.Handler(context.mainLooper)
        val runnable = Runnable {
            Toast.makeText(context, e,Toast.LENGTH_SHORT).show()
        }
        mainHandler.post(runnable)
    }

    override fun loanConditionsRequest(context: Context, toast: String) {
        if (isConnect(context)) {

            loginRepository.getBearer()?.let {
                val call = api.getLoansConditions(ActivityLoans.ACCEPT, it)
                call
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .timeout(7, TimeUnit.SECONDS)
                        .subscribe(object : Observer<LoanConditions> {
                            override fun onSubscribe(d: Disposable) {}

                            override fun onNext(loanConditions: LoanConditions) {
                                mCreateNewLoan?.setFieldItemLoanFragment(loanConditions)
                            }

                            override fun onError(e: Throwable) {
                                toastErrors(context, errors.errorToString(e.message.toString()))
                            }

                            override fun onComplete() {}
                        })
            }
        } else {
            Toast.makeText(context, toast, Toast.LENGTH_LONG).show()
        }
    }
    override fun loanRequest(context: Context, loanRequest: LoanRequest, presenter: LoansPresenter, fragmentManager: FragmentManager, toast: String) {
        if (isConnect(context)) {
            loginRepository.getBearer()?.let {
                val call = api.postGetLoans(ActivityLoans.ACCEPT, it, loanRequest)
                call
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .timeout(7, TimeUnit.SECONDS)
                        .subscribe(object : Observer<Loan> {
                            override fun onSubscribe(d: Disposable) {
                            }

                            override fun onNext(loan: Loan) {
                                val fragment = CreatedNewLoan.newInstance(Gson().toJson(loan), presenter)
                                presenter.showFragmentLeft(fragment, fragmentManager)
                            }

                            override fun onError(e: Throwable) {
                                toastErrors(context, errors.errorToString(loanRequest.javaClass.name + e.message.toString()))
                            }

                            override fun onComplete() {  }
                        })
            }
        } else {
            Toast.makeText(context, toast, Toast.LENGTH_LONG).show()
        }
    }

    override fun setListLoansToFragment() {
        doAsync {
            val listLoans = dataRepository.dbLoans.loanDao().getAll().reversed()
            dataRepository.listLoans = listLoans
            uiThread {
                mLoans?.myAdapter?.update(listLoans)
            }
        }
    }
    override fun getListLoansFromData(): List<Loan>{
        return dataRepository.listLoans
    }
    ////////////////////// ITEM
    override fun onItemRequestUpdated( name: String, famaly: String, telephone: String, amount: String ) {
        handleLoginResult(name, famaly,telephone, amount)
    }

    override fun logOut() {
        loginRepository.logOut()
    }

    private fun handleLoginResult( name: String, famaly: String, phone: String, amount: String ) {
        if (!isNameValid(name)) {
            mCreateNewLoan?.showNameError()
            mCreateNewLoan?.toggleRegButton(enable = false)
        } else if (!isFamalyValid(famaly)) {
            mCreateNewLoan?.showFamalyError()
            mCreateNewLoan?.toggleRegButton(enable = false)
        } else if (!isPhoneValid(phone)) {
            mCreateNewLoan?.showPhoneError()
            mCreateNewLoan?.toggleRegButton(enable = false)
        } else if (!isAmountValid(amount)) {
            mCreateNewLoan?.showAmountError()
            mCreateNewLoan?.toggleRegButton(enable = false)
        } else {
            mCreateNewLoan?.toggleRegButton(enable = true)
        }
    }
    private fun isNameValid(username: String): Boolean {
        return username.length > 2
    }
    private fun isFamalyValid(password: String): Boolean {
        return password.length > 1
    }
    private fun isPhoneValid(phone: String): Boolean {
        val pattern = compile("^\\+[0-9]+\\-[0-9]{3}+\\-[0-9]{3}+\\-[0-9]{2}+\\-[0-9]{2}$")
        val matcher = pattern.matcher(phone)
        return matcher.find()
    }
    private fun isAmountValid(amount: String): Boolean {
        if ( amount.length > 1){
            if (amount.toInt() > 999) return true}
        return false
    }

    override fun openQuitDialog(context: Context) {
        val quitDialog: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(
                context)
        quitDialog.setTitle(context.getString(R.string.dialog_name_exit))
        quitDialog.setPositiveButton(context.getString(R.string.yes), object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                logOut()
                (context as ActivityLoans).finish()
            }
        })
        quitDialog.setNegativeButton(context.getString(R.string.no), object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }
        })
        quitDialog.show()
    }
    override fun sortAproved(){
        mLoans?.myAdapter?.update(getListLoansFromData()!!.filter { loan -> loan.state == Adapter.APPROVED})
    }
    override fun sort(){
        mLoans?.myAdapter?.update(getListLoansFromData()!!.sortedBy { loan ->  loan.state})
    }
    override fun sortRejected(){
        mLoans?.myAdapter?.update(getListLoansFromData()!!.filter { loan -> loan.state == Adapter.REJECTED})
    }
}