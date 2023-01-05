package  com.genius.giphy.di


import com.genius.giphy.data.remote.datasource.GiphyRemoteDataSource
import com.genius.giphy.data.remote.gif.GiphyService
import com.genius.giphy.data.remote.gif.repository.GiphyRepository
import com.genius.giphy.utils.Constants.Companion.BASE_URL
import org.koin.dsl.module

val giphyModule = module {

    single { provideRetrofit(okHttpClient = get(), BASE_URL) }

    factory { createWebService<GiphyService>(retrofit = get()) }

    single { GiphyRemoteDataSource(giphyService = get()) }

    single { GiphyRepository(dao = get(), remoteSource = get()) }


}