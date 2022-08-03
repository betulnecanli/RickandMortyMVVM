package com.betulnecanli.rickandmortymvvm.data.models


import com.google.gson.annotations.SerializedName

data class ListResponse(
    @SerializedName("results")
    val details: List<Details>
)