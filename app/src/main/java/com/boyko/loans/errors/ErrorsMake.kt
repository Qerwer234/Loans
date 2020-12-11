package com.boyko.loans.errors

import com.boyko.loans.data.models.LoanRequest

class ErrorsMake {

    fun errorToString(str: String): String {
        if (str.contains("404"))
            return "Ошибка авторизации. \nНеверный логин или пароль."
        if (str.contains("event for 7 seconds"))
            return "Сервер не отвечал 7 секунд, повторите попытку позже"
        if (str.contains("LoanRequestHTTP 400"))
            return "Loan amount more than conditions max amount"
        if (str.contains("400"))
            return "Пользователь с таким логином уже зарегистрирован"
        if (str.contains("403")) {
            return "Авторизация не актуальна.\nАвторизуйтесь, пожалуйста!"
        }
        if (str.contains("Unable to resolve host")) {
            return "Нет доступа к серверу.\nВозможно отсутствует соединение на роутере "
        }

        return str
    }
}