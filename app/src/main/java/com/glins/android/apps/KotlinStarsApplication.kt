package com.glins.android.apps

import android.app.Application
import com.glins.android.apps.di.dataModule
import com.glins.android.apps.di.databaseModule
import com.glins.android.apps.di.networkModule
import com.glins.android.apps.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KotlinStarsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KotlinStarsApplication)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    dataModule,
                    viewModelModule
                )
            )
        }
    }
}