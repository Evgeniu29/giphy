package com.genius.giphy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GifItem::class], version = 1)
abstract class  AppDatabase : RoomDatabase() {
    abstract fun gifDao(): GifDao
}