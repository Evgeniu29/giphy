package com.genius.giphy.data.remote.gif.repository

import com.genius.giphy.data.local.GifDao
import com.genius.giphy.data.local.GifItem

import com.genius.giphy.data.remote.datasource.GiphyRemoteDataSource
import com.genius.giphy.data.remote.succeeded
import com.genius.giphy.data.remote.Result
import com.genius.giphy.extensions.mapToGifItem


class GiphyRepository(
    private val dao: GifDao,
    private val remoteSource: GiphyRemoteDataSource
) {

    val favouriteGifs = dao.getAll()

    suspend fun getTrendingGifs(): Result<List<GifItem>> {
        val trending = remoteSource.getTrending()
        return if (trending.succeeded) {
            Result.Success((trending as Result.Success).data.mapToGifItem())
        } else Result.Error((trending as Result.Error).exception)
    }

    suspend fun search(query: String?): Result<List<GifItem>> {
        val searchResult = remoteSource.search(query)
        return if (searchResult.succeeded) {
            Result.Success((searchResult as Result.Success).data.mapToGifItem())
        } else Result.Error((searchResult as Result.Error).exception)

    }

    suspend fun saveFavourite(gifItem: GifItem) {
        dao.insertAll(gifItem)
    }

    suspend fun deleteAllFavourites() {
        dao.deleteAllFavourites()
    }


}