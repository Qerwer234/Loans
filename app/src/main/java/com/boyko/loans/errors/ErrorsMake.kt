package com.boyko.loans.errors

class ErrorsMake (){

    fun errorToString(str: String): String {
        if (str.contains("404") == true)
            return "Ошибка авторизации. \nНеверный логин или пароль."
        if (str.contains("event for 7 seconds") == true)
            return "Сервер не отвечал 7 секунд, повторите попытку позже"
        if (str.contains("400") == true)
            return "Пользователь с таким логином уже зарегистрирован"
        if (str.contains("403") == true)
            return "Авторизация не актуальна.\nАвторизуйтесь, пожалуйста!"
        if (str.contains("Unable to resolve host") == true)
            return "Нет доступа к серверу.\nВозможно отсутствует соединение на роутере "

        return str
    }
}