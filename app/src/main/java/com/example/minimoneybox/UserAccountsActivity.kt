package com.example.minimoneybox

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minimoneybox.api.ProductsServerResponse
import com.example.minimoneybox.api.ProductsServerResponse.InvestorProduct
import kotlinx.android.synthetic.main.activity_user_accounts.*
import java.util.ArrayList

class UserAccountsActivity: AppCompatActivity() {

    private var storedProducts: ArrayList<InvestorProduct> = ArrayList()
    private lateinit var viewModel: UserAccountsViewModel
    private var authToken: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_accounts)
        viewModel = ViewModelProviders.of(this).get(UserAccountsViewModel::class.java)
        authToken = MiniMoneyboxSharedPrefs().getAuthToken(this)
        rv_products.adapter = InvestorProductsAdapter(storedProducts, this)
        viewModel.updateProducts(authToken)
        rv_products.layoutManager = LinearLayoutManager(this)
        setUpViews(viewModel)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            viewModel.updateProducts(MiniMoneyboxSharedPrefs().getAuthToken(this))
        }
    }

    private fun setUpViews(viewModel: UserAccountsViewModel) {
        setLoadingState(true)
        handleGreetingMessage()
        viewModel.investorProducts.observe(this, Observer { response ->
            if (response != null || response?.products?.isEmpty() == false) {
                val productsAdapter = rv_products.adapter
                setLoadingState(false)
                storedProducts.clear()
                storedProducts.addAll(response.products)
                productsAdapter?.notifyDataSetChanged()
                tv_user_accounts_plan_value.text = getPlanValueText(response)
            } else {
                Toast.makeText(this, R.string.products_loading_failed, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleGreetingMessage() {
        val name = MiniMoneyboxSharedPrefs().getUsername(this)
        if (name.isBlank()) {
            tv_user_accounts_greeting.visibility = View.GONE
        } else {
            tv_user_accounts_greeting.text = String.format(getString(R.string.welcome), name)
        }
    }

    private fun getPlanValueText(productsResponse: ProductsServerResponse) =
        String.format(getString(R.string.plan_value), String.format("%.0f", productsResponse.totalPlanValue.toDouble()))

    private fun setLoadingState(isLoading: Boolean) {
        user_accounts_loading_spinner.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val MONEYBOX_UPDATE_REQUEST_CODE = 1253
    }
}