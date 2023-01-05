package com.genius.giphy.data.remote.datasource

import com.genius.giphy.data.remote.gif.GiphyService
import com.genius.giphy.base.BaseDataSource

class GiphyRemoteDataSource(private val giphyService: GiphyService) : BaseDataSource() {
    suspend fun getTrending() = getResult { giphyService.getTrending() }

    suspend fun search(query: String?) = getResult {
        giphyService.search(query = query)
    }
}