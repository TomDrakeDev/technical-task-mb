package com.example.minimoneybox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minimoneybox.api.MoneyboxServiceApi
import com.example.minimoneybox.api.OneOffPaymentResponse
import com.example.minimoneybox.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IndividualAccountsViewModel: ViewModel() {

    private val _productText = MutableLiveData<String>()

    private val _moneyboxTotal = MutableLiveData<Int>()

    val productText: LiveData<String>
        get() = _productText

    val moneyboxTotal: LiveData<Int>
        get() = _moneyboxTotal

    fun setMoneyBoxText(total: Int?) {
        _moneyboxTotal.value = total
    }

    fun setProductText(text: String) {
        _productText.value = text
    }

    fun addToMoneybox(authToken: String?, productId: Int?) {
        if (!authToken.isNullOrBlank() && productId != null) {
            val service = RetrofitInstance().getRetrofitInstance().create(MoneyboxServiceApi::class.java)
            val response = service.oneOffPayment(authToken, MoneyboxServiceApi.OneOffPaymentRequestBody(20, productId))
            response.enqueue(object : Callback<OneOffPaymentResponse> {
                override fun onResponse(call: Call<OneOffPaymentResponse>, response: Response<OneOffPaymentResponse>) {
                    if (response.isSuccessful) {
                        setMoneyBoxText(response.body()?.moneyboxTotal)
                    }
                }

                override fun onFailure(call: Call<OneOffPaymentResponse>, t: Throwable) {}
            })
        }
    }
}