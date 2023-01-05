package com.genius.giphy.data.remote.model

import com.google.gson.annotations.SerializedName

data class PaginationResponseObject( @SerializedName("total_count") val count: Long)