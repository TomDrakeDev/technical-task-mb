package com.example.minimoneybox.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginServerResponse : Serializable {

    @SerializedName("Session")
    var session: Session? = null

    inner class Session {
        @SerializedName("BearerToken")
        var token: String? = null
    }
}