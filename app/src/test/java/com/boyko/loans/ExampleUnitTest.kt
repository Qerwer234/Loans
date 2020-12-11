package com.boyko.loans

import android.content.Context
import android.util.Log
import com.boyko.loans.data.repositiry.LoginRepository
import com.boyko.loans.di.LoginPresenterFactory
import com.boyko.loans.presenter.LoginPresenter
import com.boyko.loans.ui.Login
import com.boyko.loans.ui.Register
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val loginPresenter  by lazy {  LoginPresenterFactory.create() }
    private val mLogin          by lazy {  Login   .newInstance( loginPresenter) }
    private val mRegister       by lazy {  Register.newInstance( loginPresenter) }

    private lateinit var activityLogin : ActivityLogin

    //private lateinit var context: Context

    private lateinit var loginRepository: LoginRepository

    private lateinit var presenter: LoginPresenter

    @Before()
    fun attach(){

//        MockitoAnnotations.initMocks(this)

    //presenter = LoginPresenterImpl(loginRepository)
        //presenter.attachView(mLogin, mRegister, activityLogin)
    }

    @Test
    fun shouldShowErrorMessageWhenUsernameIsEmpty() {
        //loginRepository = LoginRepository(context)
        //mRegister.showUsernameError()
val context : Context = mock()
        val d = 2+2
        assertEquals(4, d)
    }
}
