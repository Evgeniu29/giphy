package com.genius.giphy.data.remote.model

import com.google.gson.annotations.SerializedName


data class Images(
    @SerializedName("fixed_height") val fixedHeight: FixedHeight,
    @SerializedName("original") val original: Original
)