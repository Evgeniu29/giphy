package com.genius.giphy.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.genius.giphy.data.local.GifItem
import com.genius.giphy.data.remote.Result
import com.genius.giphy.data.remote.gif.GiphyService
import com.genius.giphy.data.remote.gif.repository.GiphyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val giphyRepository: GiphyRepository

) : ViewModel() {

    val saveGifs = giphyRepository.favouriteGifs

    private var _gifs = MutableLiveData<Result<List<GifItem>>>()
    val gifs: LiveData<Result<List<GifItem>>>
        get() = _gifs

    init {
        getTrendingGifs()

    }


    private fun getTrendingGifs() {
        viewModelScope.launch {
            val trendingGifs = withContext(Dispatchers.IO) { giphyRepository.getTrendingGifs() }
            withContext(Dispatchers.Main) {
                _gifs.postValue(trendingGifs)
            }
        }
    }

    fun search(query: String?) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                if (!query.isNullOrEmpty()) giphyRepository.search(query) else giphyRepository.getTrendingGifs()
            }
            withContext(Dispatchers.Main) {
                _gifs.postValue(result)
            }
        }
    }

    fun saveFavourite(gifItem: GifItem) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                giphyRepository.saveFavourite(gifItem)
            }
        }
    }

    fun deleteAllFavourites() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                giphyRepository.deleteAllFavourites()
            }
        }
    }


}