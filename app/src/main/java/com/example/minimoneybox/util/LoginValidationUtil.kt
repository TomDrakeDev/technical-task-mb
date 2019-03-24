package com.example.minimoneybox.util

import java.util.regex.Pattern

class LoginValidationUtil {

    private val emailRegex = "[^@]+@[^.]+\\..+"
    private val nameRegex = "[a-zA-Z]{6,30}"
    private val passwordRegex = "^(?=.*[0-9])(?=.*[A-Z]).{10,50}$"

    fun allFieldsValid(input: LoginFieldsInput) = isValidEmail(input.email) && isValidPassword(input.password) && isValidName(input.name)

    fun isValidEmail(email: String) = Pattern.matches(emailRegex, email)

    fun isValidPassword(password: String) = Pattern.matches(passwordRegex, password)

    fun isValidName(name: String) = Pattern.matches(nameRegex, name) || name.isBlank()

    data class LoginFieldsInput(val email: String, val password: String, val name: String)

}