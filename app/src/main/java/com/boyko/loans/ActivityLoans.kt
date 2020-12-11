package com.boyko.loans

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.boyko.loans.data.repositiry.LoginRepository
import com.boyko.loans.di.LoansPresenterFactory
import com.boyko.loans.ui.CreateNewLoan
import com.boyko.loans.ui.Loans
import com.boyko.loans.ui.itemfragment.CreatedNewLoan
import com.boyko.loans.ui.itemfragment.LoanItem

const val KEY_THEME = "prefs.theme"
const val THEME_LIGHT = 0
const val THEME_DARK = 1


class ActivityLoans : AppCompatActivity() {

    private val mLoans          by lazy {  Loans.newInstance( presenter) }
    private val presenter       by lazy { LoansPresenterFactory.create( applicationContext ) }
    private val sharedPrefs     by lazy {  getSharedPreferences( PREFS_NAME, Context.MODE_PRIVATE ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loans)

        initPresenter()
        presenter.showFragmentLeft(mLoans, supportFragmentManager)
    }

    private fun initPresenter() {
        presenter.attachView(mLoans)
    }

    override fun onBackPressed() {
        when (supportFragmentManager.findFragmentById(R.id.main_loans_container)) {
            is Loans -> finish()
            is CreatedNewLoan -> {
                presenter.showFragmentRight(mLoans, supportFragmentManager)
                presenter.getAllLoans(applicationContext, getString(R.string.no_connection))
            }
            is CreateNewLoan -> presenter.showFragmentRight(mLoans, supportFragmentManager)
            is LoanItem ->      presenter.showFragmentRight(mLoans, supportFragmentManager)
            else -> super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                presenter.run { logOut() }
                finish()
            }
            R.id.action_darck -> setTheme(AppCompatDelegate.MODE_NIGHT_YES, THEME_DARK)
            R.id.action_light -> setTheme(AppCompatDelegate.MODE_NIGHT_NO, THEME_LIGHT)
        }
        return when (item.itemId) {
            R.id.action_logout -> true
            R.id.action_darck -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    private fun setTheme(themeMode: Int, prefsMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        saveTheme(prefsMode)
    }

    private fun saveTheme(theme: Int) = sharedPrefs.edit().putInt(KEY_THEME, theme).apply()

    companion object{
        const val PREFS_NAME = "prefs"
        const val KEY_NAME = "Bearer"
        const val ACCEPT ="*/*"
    }
}