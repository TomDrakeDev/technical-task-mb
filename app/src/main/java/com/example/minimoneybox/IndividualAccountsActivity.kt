package com.example.minimoneybox

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_individual_account.*

class IndividualAccountsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_account)
        val viewModel = ViewModelProviders.of(this).get(IndividualAccountsViewModel::class.java)
        val authToken = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE).getString("AUTH_TOKEN_KEY", "")

        intent?.extras?.let {
            val productText = it.getString("productDescription") ?: ""
            val moneyboxText = it.getString("productMoneybox")
            val productId = it.getInt("productId")
            viewModel.setProductText(productText)
            tv_individual_account_moneybox.text = moneyboxText
            bt_individual_account_add.setOnClickListener { viewModel.addToMoneybox(authToken, productId) }
        }

        viewModel.moneyboxTotal.observe(this, Observer {
            tv_individual_account_moneybox.text = String.format(getString(R.string.moneybox_total), it)
        })

        viewModel.productText.observe(this, Observer {
            tv_individual_account_product_description.text = it
        })
    }
}