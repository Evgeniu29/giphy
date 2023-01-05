package com.genius.giphy.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GifDao {

    @Query("SELECT * FROM Data")
    fun getAll(): LiveData<List<GifItem>>

    @Query("SELECT * FROM Data WHERE isFavourite == 1")
    fun getAllFavourites(): LiveData<List<GifItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg item: GifItem)

    @Update
    fun update(vararg gif: GifItem)

    @Delete
    fun delete(gif: GifItem)

    @Query("DELETE FROM Data WHERE isFavourite == 1")
    fun deleteAllFavourites()

}