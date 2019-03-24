package com.example.minimoneybox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.minimoneybox.api.ProductsServerResponse
import com.example.minimoneybox.api.ProductsServerResponse.InvestorProduct
import kotlinx.android.synthetic.main.activity_user_accounts.*

class UserAccountsActivity: AppCompatActivity() {

    private var storedProductsResponse: ProductsServerResponse = ProductsServerResponse()

    private lateinit var viewModel: UserAccountsViewModel
    private var authToken: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_accounts)
        viewModel = ViewModelProviders.of(this).get(UserAccountsViewModel::class.java)
        authToken = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE).getString("AUTH_TOKEN_KEY", "")
        viewModel.updateProducts(authToken)
        setUpViews(viewModel)
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateProducts(authToken)
    }

    private fun setUpViews(viewModel: UserAccountsViewModel) {
        setLoadingState(true)
        handleGreetingMessage()
        viewModel.investorProducts.observe(this, Observer { response ->
            if (response != null || response?.products?.isEmpty() == false) {
                storedProductsResponse = response
                showProducts(response)
            } else {
                Toast.makeText(this, R.string.products_loading_failed, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showProducts(response: ProductsServerResponse) {
        ll_user_accounts_product_container.removeAllViews()
        tv_user_accounts_plan_value.text = getPlanValueText(response)
        setLoadingState(false)
        response.products.forEach { addInvestorProductView(it) }
    }

    private fun addInvestorProductView(product: InvestorProduct) {
        val view = Button(ContextThemeWrapper(this, R.style.InvestorProductButtonStyle), null, 0)
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        view.text = getProductText(product)
        lp.bottomMargin = resources.displayMetrics.density.toInt() * 8
        view.layoutParams = lp
        view.setOnClickListener { startActivity(getProductIntent(product)) }
        ll_user_accounts_product_container.addView(view, lp)
    }

    private fun handleGreetingMessage() {
        val name = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE).getString("USER_NAME_KEY", "")
        if (name.isNullOrBlank()) {
            tv_user_accounts_greeting.visibility = View.GONE
        } else {
            tv_user_accounts_greeting.text = String.format(getString(R.string.welcome), name)
        }
    }

    private fun getProductIntent(product: InvestorProduct): Intent =
        Intent(baseContext, IndividualAccountsActivity::class.java).apply {
            putExtra("productDescription", product.product?.name + "\nPlan Value: £" + product.planValue)
            putExtra("productMoneybox", "Moneybox: £" + product.moneybox)
            putExtra("productId", product.Id)
        }

    private fun getPlanValueText(productsResponse: ProductsServerResponse) =
        String.format(getString(R.string.plan_value), String.format("%.0f", productsResponse.totalPlanValue.toDouble()))

    private fun getProductText(investorProduct: InvestorProduct): String = String.format(
        getString(R.string.product_description),
        investorProduct.product?.name,
        investorProduct.planValue,
        investorProduct.moneybox
    )

    private fun setLoadingState(isLoading: Boolean) {
        user_accounts_loading_spinner.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}