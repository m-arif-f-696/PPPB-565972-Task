package com.example.retrofit.model

import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("data")
    val data: Data
)