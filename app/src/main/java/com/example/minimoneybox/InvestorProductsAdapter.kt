package com.example.minimoneybox

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.minimoneybox.api.ProductsServerResponse
import kotlinx.android.synthetic.main.view_product_list_item.view.*

class InvestorProductsAdapter(val products: List<ProductsServerResponse.InvestorProduct>,
                              val context: Context) : RecyclerView.Adapter<InvestorProductsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_product_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btnProduct.text = getProductText(products[position])
        holder.btnProduct.setOnClickListener {
            (context as Activity).startActivityForResult(getProductIntent(products[position]), UserAccountsActivity.MONEYBOX_UPDATE_REQUEST_CODE)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val btnProduct: Button = view.product_list_item
    }

    private fun getProductText(investorProduct: ProductsServerResponse.InvestorProduct): String = String.format(
            context.getString(R.string.product_description),
            investorProduct.product?.name,
            investorProduct.planValue,
            investorProduct.moneybox
    )

    private fun getProductIntent(product: ProductsServerResponse.InvestorProduct): Intent =
            Intent(context, IndividualAccountsActivity::class.java).apply {
                putExtra("productDescription", product.product?.name + "\nPlan Value: £" + product.planValue)
                putExtra("productMoneybox", "Moneybox: £" + product.moneybox)
                putExtra("productId", product.Id)
            }
}