package com.boyko.loans.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.boyko.loans.ActivityLogin
import com.boyko.loans.R
import com.boyko.loans.api.Client
import com.boyko.loans.data.models.LoggedInUser
import com.boyko.loans.data.models.UserEntity
import com.boyko.loans.data.repositiry.LoginRepository
import com.boyko.loans.errors.ErrorsMake
import com.boyko.loans.ui.Login
import com.boyko.loans.ui.Register
import com.boyko.loans.util.InternetConnection
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LoginPresenterImpl(private val loginRepository: LoginRepository) : LoginPresenter {

    private var mLogin   : Login? = null
    private var mRegister: Register? = null
    private var mActivityLogin : ActivityLogin? = null
    private var errors = ErrorsMake()
    private val api = Client.apiService

    override fun attachView(
            loginFragment : Login,
            registerFragment : Register,
            activityLogin: ActivityLogin
    ) {
        this.mLogin         = loginFragment
        this.mRegister      = registerFragment
        this.mActivityLogin = activityLogin
    }

    override fun detachView() {
        this.mLogin        = null
        this.mRegister     = null
        this.mActivityLogin= null
    }

    private fun isConnect(context: Context): Boolean{
        return InternetConnection.checkConnection(context)
    }

    override fun isAuth(): Boolean{
        return loginRepository.isAuthorized()
    }

    override fun onLoginButtonClicked(
            context: Context,
            intentToStart: Intent,
            activityToFinish: Activity,
            userLoggedInUser: LoggedInUser,
            s1: String, s2: String, toast: String) {
        if (isConnect(context)) {
            api.postLogin(ACCEPT, CONTENTTYPE, userLoggedInUser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .timeout(7, TimeUnit.SECONDS)
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(authKey: String) {
                        loginRepository.authorization(authKey)
                        context.startActivity(intentToStart)
                        activityToFinish.finish()
                        Toast.makeText(context, (userLoggedInUser.name + s1), Toast.LENGTH_LONG)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        toastErrors(context, errors.errorToString(e.message.toString()))
                    }

                    override fun onComplete() {
                    }
                })
        }else {

            Toast.makeText(context, toast, Toast.LENGTH_LONG).show()
        }
    }

    private fun toastErrors(context: Context, e: String) {
        val mainHandler = android.os.Handler(context.mainLooper)
        val runnable = Runnable {
            Toast.makeText(context, e,Toast.LENGTH_LONG).show()
        }
        mainHandler.post(runnable)
    }

    override fun clickRegistration(context: Context,
                                   intent: Intent,
                                   activity: Activity,
                                   userLoggedInUser: LoggedInUser,
                                   s1: String, s2: String, toast: String) {
        api.postReg(ACCEPT, CONTENTTYPE, userLoggedInUser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .timeout(7, TimeUnit.SECONDS)
                .subscribe(object : Observer<UserEntity> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(userEntity: UserEntity) {
                        Toast.makeText(context, userEntity.name + s1, Toast.LENGTH_LONG).show()
                        onLoginButtonClicked(context, intent, activity, userLoggedInUser, s1, s2, toast)
                    }

                    override fun onError(e: Throwable) {
                        toastErrors(context, errors.errorToString(e.message.toString()))
                    }

                    override fun onComplete() {
                    }
                })
    }

    override fun clickToLogin( fragmentManager: FragmentManager) {
        mLogin?.let { showFragmentLeft(it, fragmentManager) }
    }

    private fun showFragmentRight(fragment: Fragment, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(R.anim.right_in, R.anim.right_out)
            .replace(R.id.main_container, fragment, fragment.javaClass.name)
            .commit()
    }

    override fun showFragmentLeft(fragment: Fragment, fragmentManager: FragmentManager) {
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(R.anim.left_in, R.anim.left_out)
                .replace(R.id.main_container, fragment, fragment.javaClass.name)
                .commit()
    }
    override fun clickToRegistration(fragmentManager: FragmentManager) {
        mRegister?.let { showFragmentRight(it, fragmentManager) }
    }

    override fun onLoginDataUpdated(username: String, password: String, passwordRepeat: String) {
    handleLoginResult(username, password, passwordRepeat)
    }

    private fun handleLoginResult(
            username: String,
            password: String,
            passwordrepeat: String
    ) {
        if (!isUserNameValid(username)) {
            mRegister?.showUsernameError()
            mRegister?.toggleRegButton(enable = false)
        } else if (!isPasswordValid(password)) {
            mRegister?.showPasswordError()
            mRegister?.toggleRegButton(enable = false)
        } else if (!isRepeatPasswordValid(password, passwordrepeat)) {
            mRegister?.showPasswordRepeatError()
            mRegister?.toggleRegButton(enable = false)
        } else {
            mRegister?.toggleRegButton(enable = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.isNotBlank() && username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            false
        }
    }
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
    private fun isRepeatPasswordValid(password: String, repeatPassword: String): Boolean {
        return password == repeatPassword
    }
    companion object{
        const val ACCEPT ="*/*"
        const val CONTENTTYPE ="application/json"
    }
}
