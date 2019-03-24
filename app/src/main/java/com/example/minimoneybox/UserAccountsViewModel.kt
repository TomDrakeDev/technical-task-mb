package com.example.minimoneybox

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.minimoneybox.api.MoneyboxServiceApi
import com.example.minimoneybox.api.ProductsServerResponse
import com.example.minimoneybox.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserAccountsViewModel : ViewModel() {

    private val _investorProducts = MutableLiveData<ProductsServerResponse>()

    val investorProducts: LiveData<ProductsServerResponse>
        get() = _investorProducts

    fun setProductResponse(products: ProductsServerResponse) {
            _investorProducts.value = products
    }

    fun updateProducts(authToken: String?) {
        if (!authToken.isNullOrBlank()) {
            val service = RetrofitInstance().getRetrofitInstance().create(MoneyboxServiceApi::class.java)
            val response = service.getProducts(authToken)
            response.enqueue(object : Callback<ProductsServerResponse> {
                override fun onResponse(call: Call<ProductsServerResponse>, response: Response<ProductsServerResponse>) {
                    if (response.isSuccessful) {
                        setProductResponse(response.body() ?: ProductsServerResponse())
                    }
                }

                    override fun onFailure(call: Call<ProductsServerResponse>, t: Throwable) {}
                })
            }
        }
    }