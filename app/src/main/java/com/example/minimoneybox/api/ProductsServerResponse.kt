package com.example.minimoneybox.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductsServerResponse : Serializable {

    @SerializedName("TotalPlanValue")
    var totalPlanValue: String = ""

    @SerializedName("ProductResponses")
    var products: List<InvestorProduct> = emptyList()

    class InvestorProduct {
        @SerializedName("Id")
        var Id: Int = -1

        @SerializedName("PlanValue")
        var planValue: Int = -1

        @SerializedName("Moneybox")
        var moneybox: Int = -1

        @SerializedName ("Product")
        var product: Product? = null

        class Product {
            @SerializedName("FriendlyName")
            var name: String = ""
        }
    }
}