package com.boyko.loans

import android.content.Context
import com.boyko.loans.data.repositiry.LoginRepository
import com.boyko.loans.di.LoginPresenterFactory
import com.boyko.loans.presenter.LoginPresenter
import com.boyko.loans.presenter.LoginPresenterImpl
import com.boyko.loans.ui.Login
import com.boyko.loans.ui.Register
import com.nhaarman.mockito_kotlin.verify
import kotlinx.android.synthetic.main.fragment_create_new_loan.*
import kotlinx.android.synthetic.main.registr_fragment.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Ordering
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

//    @Mock
//    private lateinit var mLogin : Login
//    @Mock
//    private lateinit var mRegister : Register
//    @Mock
//    private lateinit var activityLogin : ActivityLogin
//    @Mock
//    private lateinit var context: Context
//    //@Mock
//    private lateinit var loginRepository: LoginRepository

    //private lateinit var presenter: LoginPresenter

    @Before()
    fun attach(){


    //presenter = LoginPresenterImpl(loginRepository)
        //presenter.attachView(mLogin, mRegister, activityLogin)
    }

    @Test
    fun shouldShowErrorMessageWhenUsernameIsEmpty() {
        //loginRepository = LoginRepository(context)
        //mRegister.showUsernameError()
        assertEquals(4, 2 + 2)
    }
}
