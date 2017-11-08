package com.samplekotlin.helper.callbacks

import com.google.gson.annotations.SerializedName

class BaseCallback {

    @SerializedName("status")
    var status: Int? = null
    @SerializedName("message")
    var message: String? = null


}
