package com.genius.giphy.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Data")
data class GifItem(
    @PrimaryKey val id: String,
    val title: String,
    val gifUrl: String,
    val originalGifUrl: String,
    val width: Int,
    val height: Int,
    var isFavourite: Boolean = false
) : Parcelable