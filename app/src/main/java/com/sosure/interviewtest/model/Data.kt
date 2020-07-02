package com.sosure.interviewtest.model

import com.google.gson.annotations.SerializedName

data class People(
    @SerializedName("name")
    val name: String?
)