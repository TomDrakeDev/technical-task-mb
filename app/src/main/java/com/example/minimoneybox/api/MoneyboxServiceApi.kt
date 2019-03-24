package com.example.minimoneybox.api

import retrofit2.Call
import retrofit2.http.*

interface MoneyboxServiceApi {

    @Headers(
        "AppId: 3a97b932a9d449c981b595",
        "Content-Type: application/json",
        "appVersion: 5.10.0",
        "apiVersion: 3.0.0"
    )
    @POST("users/login")
    fun login(@Body body: LoginRequestBody): Call<LoginServerResponse>

    @Headers(
        "AppId: 3a97b932a9d449c981b595",
        "Content-Type: application/json",
        "appVersion: 5.10.0",
        "apiVersion: 3.0.0"
    )
    @GET("investorproducts")
    fun getProducts(@Header("Authorization") auth: String): Call<ProductsServerResponse>

    @Headers(
        "AppId: 3a97b932a9d449c981b595",
        "Content-Type: application/json",
        "appVersion: 5.10.0",
        "apiVersion: 3.0.0"
    )
    @POST("oneoffpayments")
    fun oneOffPayment(@Header("Authorization") auth: String, @Body body: OneOffPaymentRequestBody): Call<OneOffPaymentResponse>

    class LoginRequestBody(val email: String, val password: String, val idfa: String = "ANYTHING")

    class OneOffPaymentRequestBody(val amount: Int, val investorProductId: Int)
}