package com.genius.giphy.di


import android.app.DownloadManager
import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.room.Room
import androidx.viewbinding.BuildConfig
import com.genius.giphy.data.local.AppDatabase
import com.genius.giphy.model.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    factory { providesHttplogging() }
    factory { providesOkHttpClient(get()) }

}

fun provideRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun providesOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(interceptor).build()
}

fun providesHttplogging(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
    else
        HttpLoggingInterceptor.Level.NONE
    return interceptor
}

inline fun <reified T> createWebService(retrofit: Retrofit): T = retrofit.create(T::class.java)


val dbModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "data"
        )
            .build()
    }
    single { get<AppDatabase>().gifDao() }
}

val mainModule = module {

    single { androidContext().getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager }
    single { androidContext().getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager }
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    viewModel { MainViewModel(giphyRepository = get()) }

}


