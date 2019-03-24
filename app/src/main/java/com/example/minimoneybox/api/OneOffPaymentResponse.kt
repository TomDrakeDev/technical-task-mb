package com.example.minimoneybox.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OneOffPaymentResponse: Serializable {
    @SerializedName("Moneybox")
    var moneyboxTotal: Int = 0
}