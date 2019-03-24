package com.example.minimoneybox.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

class RetrofitInstance {
    private lateinit var retrofit: Retrofit
    private val baseUrl = "https://api-test01.moneyboxapp.com/"

    fun getRetrofitInstance(): Retrofit {
        val gson = GsonBuilder()
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addNetworkInterceptor(object : Interceptor {

                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    val request = chain
                        .request()
                        .newBuilder()
                        .build()
                    return chain.proceed(request)
                }
            }).build()

        retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit
    }
}