package com.genius.giphy

import android.app.Application
import com.genius.giphy.di.dbModule
import com.genius.giphy.di.giphyModule
import com.genius.giphy.di.mainModule
import com.genius.giphy.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class GifApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    mainModule,
                    networkModule,
                    dbModule,
                    giphyModule,
                    mainModule,

        )
            )
        }

    }

}