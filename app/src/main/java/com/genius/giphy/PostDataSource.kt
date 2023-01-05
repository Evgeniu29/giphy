package com.genius.giphy

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.genius.giphy.data.local.GifItem
import com.genius.giphy.data.remote.gif.GiphyService

import com.genius.giphy.utils.Config

class PostDataSource(private val apiService: GiphyService) : PagingSource<Int, GifItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,GifItem> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getTrending(Config.KEY)
            val responseData = mutableListOf<GifItem>()
            val data = response.body()?.data ?: emptyList()
            responseData.addAll(responseData)
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, GifItem>): Int? {
        TODO("Not yet implemented")
    }

}
