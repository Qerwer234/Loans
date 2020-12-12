package com.boyko.loans

import android.content.Context
import android.provider.Settings.Global.getString
import android.text.Editable
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.EditText
import com.boyko.loans.di.LoginPresenterFactory
import com.boyko.loans.presenter.LoginPresenter
import com.boyko.loans.ui.Login
import com.boyko.loans.ui.Register
import com.nhaarman.mockito_kotlin.verify
import kotlinx.android.synthetic.main.login_fragment.*
import org.jetbrains.anko.custom.ankoView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.jar.Attributes
import java.util.zip.Inflater

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    lateinit var pre : LoginPresenter

    @Mock
    lateinit var mReg : Register
    @Mock
    lateinit var mLogin : Login
    @Mock
    lateinit var mAL : ActivityLogin
    @Mock
    lateinit var context : Context

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        pre  = LoginPresenterFactory.create(context)
        pre.attachView(mLogin, mReg, mAL)
    }
    @Test
    fun shouldShowErrorMessageWhenUsernameIsEmpty() {

        `when`(pre.isAuth()).thenReturn(false)

        verify(pre).isAuth()
    }
}