package com.genius.giphy.data.remote.model

import com.google.gson.annotations.SerializedName


data class GifResult(
    @SerializedName("data") val `data`: List<Data>,
    @SerializedName("pagination") val pagination: PaginationResponseObject

)