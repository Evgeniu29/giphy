package com.genius.giphy.data.remote.model

import com.google.gson.annotations.SerializedName

data class Original(
    val height: Int,
    val width: Int,
    val size: String,
    @SerializedName("url") val gifUrl: String,
    @SerializedName("webp") val webpUrl: String
)