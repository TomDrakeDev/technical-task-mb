package com.example.minimoneybox

import com.example.minimoneybox.api.MoneyboxServiceApi
import com.example.minimoneybox.api.RetrofitInstance
import java.util.regex.Pattern
import com.example.minimoneybox.api.LoginServerResponse
import com.example.minimoneybox.util.LoginValidationUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(private val view: View) {

    fun validateFields(input: LoginValidationUtil.LoginFieldsInput) {
        if (LoginValidationUtil().allFieldsValid(input)) {
            loginRequest(LoginCredentials(input.email, input.password), input.name)
        } else {
            view.setInputError(
                LoginValidationUtil().isValidEmail(input.email),
                LoginValidationUtil().isValidPassword(input.password),
                LoginValidationUtil().isValidName(input.name))
        }
    }

    private fun loginRequest(credentials: LoginCredentials, name: String?) {
        val service = RetrofitInstance().getRetrofitInstance().create(MoneyboxServiceApi::class.java)
        val response = service.login(MoneyboxServiceApi.LoginRequestBody(credentials.email, credentials.password))
        response.enqueue(object : Callback<LoginServerResponse> {
            override fun onResponse(call: Call<LoginServerResponse>, response: Response<LoginServerResponse>) {
                if (response.isSuccessful){
                    val authToken = response.body()?.session?.token ?: ""
                    view.login(authToken, name)
                } else {
                    view.showLoginError()
                }
            }

            override fun onFailure(call: Call<LoginServerResponse>, t: Throwable) {
                view.showLoginError()
            }
        })
    }

    interface View {
        fun login(authToken: String, name: String?)
        fun showLoginError()
        fun setInputError(emailValid: Boolean, passwordValid: Boolean, nameValid: Boolean)
    }

    data class LoginCredentials(val email: String, val password: String, val idfa: String = "ANYTHING")
}